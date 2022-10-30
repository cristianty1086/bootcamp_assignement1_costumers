package com.nttdata.bootcamp.assignement1.clients.utilities;

import java.math.BigInteger;

public class BuilderUrl {
    public static String buildGetCostumer(BigInteger costumerId) {
        if(costumerId == null) {
            return  null;
        }

        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.getCurrentUrl());
        ss.append("/costumer/get/");
        ss.append(costumerId);

        return ss.toString();
    }
    public static String buildGetCostumerBankAccounts(BigInteger costumerId) {
        if(costumerId == null) {
            return  null;
        }

        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.baseUrlBankAccounts);
        ss.append("/bank_account/get_by_costumer/");
        ss.append(costumerId);

        return ss.toString();
    }
    public static String buildGetCostumerCredits(BigInteger costumerId) {
        if(costumerId == null) {
            return  null;
        }

        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.baseUrlCredits);
        ss.append("/credit/get_by_costumer/");
        ss.append(costumerId);

        return ss.toString();
    }
    public static String buildCountCreditCards(BigInteger costumerId) {
        if(costumerId == null) {
            return null;
        }

        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.baseUrlCredits);
        ss.append("/credit/count_credits_cards/");
        ss.append(costumerId);

        return ss.toString();
    }
}
