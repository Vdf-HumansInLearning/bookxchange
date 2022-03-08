package com.bookxchange.service;

import com.bookxchange.model.TransactionEntity;
import com.bookxchange.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
//todo update la quantity in books verify if Rented, do not rent anymore
    public TransactionEntity createTransaction(TransactionEntity transactionEntity){
        return transactionRepository.save(transactionEntity);

    }
    public List<TransactionEntity> getTransactionByUserID(String id){
        return transactionRepository.findAllByMemberIdFrom(id);
    }
    public List<TransactionEntity> getTransactionByType(String transactionType){
        return transactionRepository.findByTransactionType(transactionType);
    }




//    TransactionRepo transactionRepo = new TransactionRepo();
//    MarketBookRepo marketBookRepo = new MarketBookRepo();
//    MemberRepo memberRepo = new MemberRepo();
//
//    public static void main(String[] args) throws SQLException, IOException {
//        TransactionService transactionService = new TransactionService();
//        MarketBookRepo marketBookRepo = new MarketBookRepo();
//
//        MarketBook marketBook1 =
//                marketBookRepo.getMarketBook(UUID.fromString("1c821fb0-1024-4cd0-8f23-2d763fb2c13b"));
//        MarketBook marketBook2 =
//                marketBookRepo.getMarketBook(UUID.fromString("eb1ab8b2-a2a4-4057-bba2-2d2caf65ce47"));
//
//        transactionService.rentBook(
//                marketBook1, UUID.fromString("13177e99-14b5-43c5-a446-e0dc751c3153"));
////        transactionService.buyBook(
////                marketBook2, UUID.fromString("13177e99-14b5-43c5-a446-e0dc751c3153"));
//
//        transactionService.buyBookWithPoints(marketBook2, UUID.fromString("ae677979-ffec-4a90-a3e5-a5d1d31c0ee9"));
//
//        System.out.println("merge");
//    }
//
//    public boolean rentBook(MarketBook marketBook, UUID memberWhoRents) throws SQLException {
//        if (marketBook.getBookStatus().equals(BookStatus.AVAILABLE) && marketBook.isForRent()) {
//            Transaction rentTransaction =
//                    new Transaction(
//                            marketBook.getId(), marketBook.getUserId(), memberWhoRents, TransactionType.RENT);
//            transactionRepo.createTransaction(rentTransaction);
//            marketBookRepo.changeBookStatusInDb(marketBook);
//            memberRepo.updatePointsToMember(marketBook);
//            return true;
//        }
//        return false;
//
//    }
//
//    public boolean buyBook(MarketBook marketBook, UUID memberWhoBuys) throws SQLException {
//        if (marketBook.getBookStatus().equals(BookStatus.AVAILABLE) && marketBook.isForSell()) {
//            Transaction sellTransaction = new Transaction(
//                    marketBook.getId(), marketBook.getUserId(), memberWhoBuys, TransactionType.SOLD);
//            transactionRepo.createTransaction(sellTransaction);
//            marketBookRepo.changeBookStatusInDb(marketBook);
//            return true;
//
//        }
//        return false;
//    }
//
//    //TODO check points <0, or number of points if the transacation can be made
//    public boolean buyBookWithPoints(MarketBook marketBook, UUID memberWhoBuys) throws SQLException {
//        if (marketBook.getBookStatus().equals(BookStatus.AVAILABLE) && marketBook.isForSell()) {
//            if(memberRepo.getPointsForMember(memberWhoBuys) >= memberRepo.convertMoneyToPoints(marketBook)){
//                buyBook(marketBook, memberWhoBuys);
//                memberRepo.updatePointsAfterBuy(marketBook,memberWhoBuys);
//                return true;
//            }else {
//                throw new NotEnoughPointsException("You don't have enough points to buy this book");
//            }
//        }
//        return false;
//    }
}
