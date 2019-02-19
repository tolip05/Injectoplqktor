package panda.service;

import org.modelmapper.ModelMapper;
import panda.domein.entities.Receipt;
import panda.domein.models.service.ReceiptServiceModel;
import panda.repository.ReceiptRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final ModelMapper modelMapper;

    @Inject
    public ReceiptServiceImpl(ReceiptRepository receiptRepository, ModelMapper modelMapper) {
        this.receiptRepository = receiptRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void packageCreate(ReceiptServiceModel receiptServiceModel) {
        Receipt receipt = this.modelMapper.map(receiptServiceModel,Receipt.class);
        this.receiptRepository.save(receipt);
    }

    @Override
    public List<ReceiptServiceModel> getAllReceipts() {
        List<Receipt> receipts = this.receiptRepository.findAll();
        List<ReceiptServiceModel>receiptServiceModels = new ArrayList<>();
        for (Receipt receipt : receipts) {
            ReceiptServiceModel receiptServiceModel =
                    this.modelMapper.map(receipt,ReceiptServiceModel.class);
        receiptServiceModels.add(receiptServiceModel);
        }

        return receiptServiceModels;
    }

    @Override
    public ReceiptServiceModel findById(String id) {
        ReceiptServiceModel receiptServiceModel =
                this.modelMapper
                        .map(this.receiptRepository.findById(id),ReceiptServiceModel.class);
        return receiptServiceModel;
    }


}
