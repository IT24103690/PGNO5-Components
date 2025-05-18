package com.InventoryManagement.Stork.repos;


import com.InventoryManagement.Stork.services.ProductService;
import com.InventoryManagement.Stork.models.Requests;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class requestFileHandler {
    private static final String FILE_PATH = "data/requests.txt";

    private static File getFile() {
        return new File(FILE_PATH);
    }

    public static List<Requests> getAllRequests() {
        List<Requests> requestsList = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(getFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if(data.length == 6){
                    requestsList.add(new Requests(
                            Integer.parseInt(data[0].trim()),
                            ProductService.findProductById(data[1].trim()),
                            Integer.parseInt(data[2].trim()),
                            Integer.parseInt(data[3].trim()),
                            Boolean.parseBoolean(data[4].trim()),
                            Integer.parseInt(data[5].trim())
                    ));
                }
            }
            return requestsList;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void writeRequests(List<Requests> requests) {
        System.out.println("Writing " + requests.size() + " requests to file...");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFile()))) {
            for (Requests request : requests) {
                String productId = request.getProduct() != null ? request.getProduct().getId() : null;
                writer.write(String.format("%d,%s,%d,%d,%b,%d",
                        request.getRequestId(),
                        productId,
                        request.getAmountFilled(),
                        request.getFullAmountRequested(),
                        request.isVisibleToSupplier(),
                        request.getFirstRequestId()));
                writer.newLine();
            }
            System.out.println("Successfully wrote requests to file.");
        } catch (IOException e) {
            System.err.println("Failed to write requests to file:");
            e.printStackTrace();
        }
    }

    public static Requests getRequestById(int id) {
        List<Requests> requests = getAllRequests();
        for (Requests request : requests) {
            if (request.getRequestId() == id) {
                return request;
            }
        }
        return null;
    }

    public static void deleteRequestById(int id) {
        List<Requests> requestList = getAllRequests();
        requestList.removeIf(r -> r.getRequestId() == id);
        writeRequests(requestList);
    }

    public static void addRequest(Requests request) {
        List<Requests> requests = getAllRequests();
        requests.add(request);
        writeRequests(requests);
    }
}
