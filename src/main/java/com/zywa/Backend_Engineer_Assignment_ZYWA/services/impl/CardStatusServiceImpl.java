package com.zywa.Backend_Engineer_Assignment_ZYWA.services.impl;

import com.zywa.Backend_Engineer_Assignment_ZYWA.convertor.CardStatusConvertor;
import com.zywa.Backend_Engineer_Assignment_ZYWA.dto.CardStatusDto;
import com.zywa.Backend_Engineer_Assignment_ZYWA.dto.CardStatusQueryDto;
import com.zywa.Backend_Engineer_Assignment_ZYWA.entity.CardStatus;
import com.zywa.Backend_Engineer_Assignment_ZYWA.repositories.CardStatusRepository;
import com.zywa.Backend_Engineer_Assignment_ZYWA.services.CardStatusService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CardStatusServiceImpl implements CardStatusService {

    @Autowired
    private CardStatusConvertor cardStatusConvertor;

    @Autowired
    private CardStatusRepository cardStatusRepository;

    @Value("${project.data}")
    private String path;

    @Override
    public String getCardStatus(CardStatusQueryDto cardStatusQueryDto) {
        String status = "";
        if (cardStatusQueryDto.getCardId() != null) {
            status = cardStatusRepository.getCardStatusByCardId(cardStatusQueryDto.getCardId());
        } else if (cardStatusQueryDto.getPhoneNumber() != null) {
            status = cardStatusRepository.getCardStatusByUserContact(
                cardStatusQueryDto.getPhoneNumber());
        } else {
            return "Provide either Card Id or Phone Number in order to search";
        }
        return status;
    }

    @Override
    public void processUploadedFiles() {
        try {
            String[] csvFiles = getAllCsvFilesInFolder(path);

            for (String csvFile : csvFiles) {
                processCsvFile(csvFile);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            deleteAllFilesInFolder(path);
        }
    }

    private String[] getAllCsvFilesInFolder(String folderPath) {
        File folder = new File(folderPath);
        return folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".csv");
            }
        });
    }

    private void deleteAllFilesInFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".csv");
            }
        });

        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    private void processCsvFile(String fileName) throws FileNotFoundException {
        File file = new File(path + File.separator + fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line = br.readLine();

            while (line != null) {
                convertCsvLineToDto(line, fileName);
                line = br.readLine();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void convertCsvLineToDto(String line, String fileName) throws ParseException {
        String[] data = line.split(",");
        if (data.length >= 4) {
            String cardId = data[1].trim();
            String userContact = getPhoneNumber(data[2].trim());
            Date timeStamp = parseDate(data[3].trim());
            String status = null;
            if (!fileName.contains("Pickup") && !fileName.contains("Returned")) {
                status = data[4].trim();
            } else {
                if (fileName.contains("Pickup")) {
                    status = "Pickup";
                } else {
                    status = "Returned";
                }
            }
            CardStatus cardStatusDetails = cardStatusRepository.getCardByCardId(data[1].trim());
            if (cardStatusDetails != null) {
                if (cardStatusDetails.getTimeStamp().compareTo(timeStamp) < 0) {
                    cardStatusDetails.setTimeStamp(timeStamp);
                    cardStatusDetails.setStatus(status);
                    cardStatusRepository.save(cardStatusDetails);
                }
            } else {
                CardStatusDto cardStatusDto = CardStatusDto.Builder.cardStatusDto()
                    .withCardId(cardId)
                    .withUserContact(userContact)
                    .withTimeStamp(timeStamp)
                    .withStatus(status)
                    .build();
                if (cardStatusDto != null) {
                    CardStatus cardStatus = cardStatusConvertor.convert(cardStatusDto);
                    cardStatusRepository.save(cardStatus);
                }
            }
        }
    }

    private Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat[] dateFormats = {
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"),
            new SimpleDateFormat("dd-MM-yyyy HH:mm"),
            new SimpleDateFormat("dd-MM-yyyy hh:mma"),
            new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        };

        for (SimpleDateFormat dateFormat : dateFormats) {
            try {
                // Attempt to parse the date using the current format
                return dateFormat.parse(dateStr);
            } catch (ParseException e) {
                // Ignore and try the next format
            }
        }

        throw new ParseException("Unrecognized date format: " + dateStr, 0);
    }

    private static String getPhoneNumber(String input) {
        String regex = "\\d{9}";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            input = matcher.group();
        }
        return input;
    }
}