package panda.service;

import panda.domein.models.service.ReceiptServiceModel;

import java.util.List;

public interface ReceiptService {



    public void packageCreate(ReceiptServiceModel receiptServiceModel);


    List<ReceiptServiceModel> getAllReceipts();

    ReceiptServiceModel findById(String id);


}
