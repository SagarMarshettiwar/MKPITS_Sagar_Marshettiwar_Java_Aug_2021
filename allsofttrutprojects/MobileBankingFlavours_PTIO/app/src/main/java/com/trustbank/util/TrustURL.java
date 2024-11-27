package com.trustbank.util;

@SuppressWarnings("ALL")
public class TrustURL {

    //----------------------------url-----------------------------------------/
    public static String AuthenticateUserUrl() {
        if (AppConstants.IP != null) {
            return AppConstants.IP + "/mbank.svc/api/authenticate";
        } else {
            return "";
        }
    }

    public static String LogoutUserUrl() {
        if (AppConstants.IP != null) {
            return AppConstants.IP + "/mbank.svc/api/logout";
        } else {
            return "";
        }
    }

    public static String GetMenuListUrl() {
        if (AppConstants.IP != null) {
            return AppConstants.IP + "/mbank.svc/api";
        } else {
            return "";
        }
    }

    public static String LocateAtmUrl(String state, String city) {
        if (AppConstants.IP != null) {
            return AppConstants.IP + "/assets/atm_list.json?state=" + state + "&city=" + city;
        } else {
            return "";
        }
    }

    public static String ContactUsUrl() {
        if (AppConstants.IP != null) {
            return AppConstants.IP + "/assets/contact.json";
        } else {
            return "";
        }
    }


    public static String SecurityHintUrl() {
        if (AppConstants.IP != null) {
            return AppConstants.IP + "/assets/app_assets.json";
        } else {
            return "";
        }
    }

    public static String BranchesUrl(String state, String city) {
        if (AppConstants.IP != null) {
            return AppConstants.IP + "/assets/branch_list.json?state=" + state + "&city=" + city;
        } else {
            return "";
        }
    }

    public static String MobileNoVerifyUrl() {
        if (AppConstants.IP != null) {
            return AppConstants.IP + "/mbank.svc/api";
        } else {
            return "";
        }
    }

    public static String GeneratePinUrl() {
        if (AppConstants.IP != null) {
            return AppConstants.IP + "/mbank.svc/api/generate_pin";
        } else {
            return "";
        }
    }

    public static String GetProfileAccountsAndChequeBookDetailsUrl(String mobileNo, String mClientId) {
        if (AppConstants.IP != null) {
            return AppConstants.IP + "/mbank.svc/api?mobile_number=" + mobileNo + "&custid=" + mClientId;
        } else {
            return "";
        }
    }

    public static String GenerateStanRrnUrl() {
        if (AppConstants.IP != null) {
            return AppConstants.IP + "/mbank.svc/api/generate_stan_rrn";
        } else {
            return "";
        }
    }

    public static String GenerateOtpFundTransferUrl() {
        if (AppConstants.IP != null) {
            return AppConstants.IP + "/mbank.svc/api/generate_otp";
        } else {
            return "";
        }
    }

    public static String getAboutUsDetails() {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/trustBank.Wcf.NetBanking.CbsNetBank.svc/GetListByTag?args=%3Cdata%3E%3Ctag%3EABOUT_US%3C/tag%3E%3C/data%3E";
            return url;
        } else {
            return "";
        }
    }

    public static String getTermsConditions() {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/assets/mbank_terms_and_conditions.html";
            return url;
        } else {
            return "";
        }
    }

    public static String getAdevertisement() {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/folder_content.aspx";
            return url;
        } else {
            return "";
        }
    }

    public static String getAccountDetails(String accno) {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/mbank.svc/api?account_number=" + accno;
            return url;
        } else {
            return "";
        }
    }

    public static String getURLForFundTransferOwnAndNeft() {
        if (AppConstants.IP != null) {
            return AppConstants.IP + "/mbank.svc/api";
        } else {
            return "";
        }
    }

    public static String GetCertificate(String Accno,String userid) {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/mbank.svc/api?accountno="+Accno+"&userid="+userid;
            return url;
        } else {
            return "";
        }
    }

    public static String GetSchedule(String name, String email, String mobileno,
                                     String branch, String date,
                                     String time,String userid,String remarks) {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/mbank.svc/api?name="+name+"&email="+email+"&contact="+mobileno+"&branch="+branch+"&date="+date+"&time="+time+"&userid="+userid+"&remarks="+remarks;
            return url;
        } else {
            return "";
        }
    }

    public static String GetdetailCatalogue(String lang) {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/mbank.svc/api?lang="+lang;
            return url;
        } else {
            return "";
        }
    }

    public static String Getoverview(String clientid, String acc) {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/mbank.svc/api?clientid="+clientid+"&accountno="+acc;
            return url;
        } else {
            return "";
        }
    }

    public static String GetCatalogue() {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/mbank.svc/api";
            return url;
        } else {
            return "";
        }
    }

    public static String getProduct(String acctypecode, String extracolumn) {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/mbank.svc/api?acctypecode="+acctypecode+"&extracolumn="+extracolumn;
            return url;
        } else {
            return "";
        }
    }

    public static String getLoanRepayment(String chartId, String expiryDate, String installemnt, String installemntID1, String dueDate, String dueDate1, String creditAmount, String intrstRate, String creditDate, String loanperiod, String userId, String insurance, String insuranceRate, String intrstCal) {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/mbank.svc/api?emiCalculationType="+chartId+"&expiryDate="+expiryDate+"&installmentApplicationFrequency="+installemnt+"&interestCompoundingFrequency="+installemntID1+"&firstInstallmentDate="+dueDate+"&EffectiveDate="+dueDate1+"&disbursedAmount="+creditAmount+"&interestRate="+intrstRate+"&disbursementDate="+creditDate+"&periodInMonths="+loanperiod+"&userid="+userId+"&isinsurance="+insurance+"&insurancerate="+insuranceRate+"&intcalbase="+intrstCal;
            return url;
        } else {
            return "";
        }
    }
    public static String getLoanRepaymentDueDate(String accno, String date) {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/mbank.svc/api?accountno=" + accno+"&workingDate="+date;
            return url;
        } else {
            return "";
        }
    }

    public static String getInvestmentClosure(String accno, String operation) {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/mbank.svc/api?acc_no=" + accno+"&operation="+operation;
            return url;
        } else {
            return "";
        }
    }

    public static String getInvestmentClosure(String accno, String operation, String remark, String clientid) {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/mbank.svc/api?acc_no=" + accno+"&operation="+operation+"&remark="+remark+"&clientid="+clientid;
            return url;
        } else {
            return "";
        }
    }

    public static String getInstallDate(String a1, String a2,String a3) {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/mbank.svc/api?disbdate="+a1+"&moratorioumperiod="+a2+"&repaymentday="+a3;
            return url;
        } else {
            return "";
        }
    }
    public static String getInterestRate(String scheamid, String date,String money,String loanperiod) {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/mbank.svc/api?schemeid="+scheamid+"&sanctiondate="+date+"&sanctionamount="+money+"&loanperiod="+loanperiod;
            return url;
        } else {
            return "";
        }
    }

    public static String Getslab(String clientid ,String tag) {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/mbank.svc/api?clientid=" + clientid+"&tag="+tag;
            return url;
        } else {
            return "";
        }
    }

    public static String GetOpenAccount(String clientid, String productid, String remarks, String openingtype, String needCheque) {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/mbank.svc/api?clientid="+clientid+"&productid="+productid+"&description="+remarks+"&openingtype="+openingtype+"&ChequeBookRequired="+needCheque;
            return url;
        } else {
            return "";
        }
    }

    public static String investmentCalculator(String calType, String calMethod, String investmentValue, String invstduration, String totalInvestmentValue, String interestRate,
                                              String extraInterestRate, String compFreqValue ) {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/mbank.svc/api?calculationtype="+calType+"&calculationmode="+ calMethod+"&compoundfrequency="+  compFreqValue+"&investmentvalue="+  investmentValue
                    +"&totalinvestmentvalue="+  totalInvestmentValue+"&investmentduration="+  invstduration+"&interestrate="+  interestRate+
                    "&extrainterestrate="+ extraInterestRate;
            return url;
        } else {
            return "";
        }
    }

    public static String getSettingStatustype(String profile) {
        if (AppConstants.IP != null) {
            String url = AppConstants.IP + "/mbank.svc/api?profile_id=" + profile;
            return url;
        } else {
            return "";
        }
    }
}