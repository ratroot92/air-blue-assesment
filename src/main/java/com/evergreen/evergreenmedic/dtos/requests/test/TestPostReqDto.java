package com.evergreen.evergreenmedic.dtos.requests.test;

import jakarta.validation.constraints.AssertTrue;
import lombok.Data;

@Data
public class TestPostReqDto {


    private Integer receiverAccountId;
    private Integer paymentContactId;

    //    @NotNull
//    @NotBlank
    private String reqType;


    @AssertTrue(message = "you need to provide one value not both")
    public boolean hasReqTypeBothValues() {
        System.out.println("getting req type " + this.receiverAccountId + this.paymentContactId);
        if (this.receiverAccountId != null && this.paymentContactId != null) {
            System.out.println("getting req type");
            return false;
//            throw new BadRequestException("its an error");
        }

        return true;
//        return this.receiverAccountId == null && this.paymentContactId == null;
//        if (receiverAccountId == null) {
//            return String.valueOf(paymentContactId);
//        }
//        return String.valueOf(receiverAccountId);
    }

    @AssertTrue(message = "Please provide at least one value")
    public boolean hasReqTypeNonValue() {
        System.out.println("getting req type " + this.receiverAccountId + this.paymentContactId);
//        if (this.receiverAccountId != null && this.paymentContactId != null) {
//            System.out.println("getting req type");
//            return false;
////            throw new BadRequestException("its an error");
//        }

        return !(this.receiverAccountId == null && this.paymentContactId == null);
//        if (receiverAccountId == null) {
//            return String.valueOf(paymentContactId);
//        }
//        return String.valueOf(receiverAccountId);
    }


//    public void setAge(Integer paymentContactId) {
//        System.out.println("=========================");
//        System.out.println("paymentContactId => " + paymentContactId);
//        System.out.println("=========================");
//        if (paymentContactId != null) {
//            this.paymentContactId = paymentContactId;
//            this.reqType = "send-money";
//        }
//    }
//
//    public void setReceiverAccountId(Integer receiverAccountId) {
//        System.out.println("=========================");
//        System.out.println("setReceiverAccountId => " + receiverAccountId);
//        System.out.println("=========================");
//        if (receiverAccountId != null) {
//            this.receiverAccountId = receiverAccountId;
//            this.reqType = "transfer";
//        }
//    }

}
