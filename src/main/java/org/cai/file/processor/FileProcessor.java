package org.cai.file.processor;

import org.cai.file.type.FASTA;
import scala.Tuple2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cai584770
 * @date 2024/10/15 17:12
 * @Version
 */
public class FileProcessor {

    public static FASTA getFASTAFromFile(String filePath) {
        String[] informationAndSequence = FileProcessor.getInformationAndSequence(filePath);

        return new FASTA(informationAndSequence[0], informationAndSequence[1]);
    }


    public static String getSequenceFromFASTQ(String filePath) {
        StringBuilder sequenceBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNext()) {
                scanner.next();
                sequenceBuilder.append(scanner.next());
                scanner.next();
                scanner.next();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sequenceBuilder.toString();
    }

    public static String[] getInformationAndSequence(String filePath) {
        String information = "";
        String sequence = "";
        try {
            Scanner scanner = new Scanner(new File(filePath));
            information = scanner.nextLine();
            sequence = scanner.useDelimiter("\\Z").next();
            scanner.close();
            sequence = FileNormalize.remove(sequence);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new String[]{information.substring(1), sequence};
    }

    public static String processFile(String filePath) {
        String information = "";
        String sequence = "";
        try {
            Scanner scanner = new Scanner(new File(filePath));
            information = scanner.nextLine();
            sequence = scanner.useDelimiter("\\Z").next();
            scanner.close();

            sequence = FileNormalize.normalize(FileNormalize.remove(sequence));

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(information + "\n" + sequence);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return information.length() > 1 ? information.substring(1) : information;
    }

    public static void processFile(String filePath, String folderPath) {
        createFolder(folderPath);
        char delimiter = '>';
        try (Scanner scanner = new Scanner(new File(filePath))) {
            StringBuilder buffer = new StringBuilder();
            while (scanner.hasNext()) {
                char charRead = scanner.next().charAt(0);
                if (charRead == delimiter && buffer.length() > 0) {
                    String result = buffer.toString();
                    String information = getInformation(result);
                    String sequence = result.replace(information, "");
                    sequence = FileNormalize.normalize(FileNormalize.remove(sequence));

                    String chromosomeName = getChromosomeName(information);
                    String tempPath = folderPath + "/" + chromosomeName + ".fa";
                    writeStringToFile(">" + information, tempPath, false);
                    writeStringToFile(sequence, tempPath, true);

                    buffer.setLength(0);
                } else {
                    buffer.append(charRead);
                }
            }
            if (buffer.length() > 0) {
                String result = buffer.toString();
                String information = getInformation(result);
                String sequence = result.replace(information, "");
                sequence = FileNormalize.normalize(FileNormalize.remove(sequence));

                String chromosomeName = getChromosomeName(information);
                String tempPath = folderPath + "/" + chromosomeName + ".fa";
                writeStringToFile(">" + information + "\n", tempPath, false);
                writeStringToFile(sequence, tempPath, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getInformation(String data) {
        int newlineIndex = data.indexOf('\n');
        return newlineIndex != -1 ? data.substring(0, newlineIndex + 1) : data;
    }

    public static String getChromosomeName(String data) {
        Pattern pattern = Pattern.compile("(?i)chr(\\w*)");
        Matcher matcher = pattern.matcher(data);
        return matcher.find() ? "chr" + matcher.group(1) : "";
    }

    public static void writeStringToFile(String content, String filePath, boolean append) {
        try {
            Path path = Paths.get(filePath);
            if (append) {
                Files.write(path, content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } else {
                Files.write(path, content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void createFolder(String folderPath) {
        Path path = Paths.get(folderPath);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                System.out.println("Error creating directory: " + e.getMessage());
            }
        } else {
            System.out.println("Directory '" + folderPath + "' already exists.");
        }
    }

    public static String readToString(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                contentBuilder.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public static void writeBytesToFile(byte[] byteArray, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] readBytesFromFile(String fileName) {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            byte[] fileBytes = new byte[0];
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] temp = new byte[fileBytes.length + bytesRead];
                System.arraycopy(fileBytes, 0, temp, 0, fileBytes.length);
                System.arraycopy(buffer, 0, temp, fileBytes.length, bytesRead);
                fileBytes = temp;
            }
            return fileBytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}



