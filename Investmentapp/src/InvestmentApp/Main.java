package InvestmentApp;

import java.util.Scanner;
import java.text.DecimalFormat;

// Custom exception for invalid investment amount
class InvalidInvestmentAmountInputException extends Exception {
    public InvalidInvestmentAmountInputException(String message) {
        super(message);
    }
}

// Custom exception for invalid fund selection
class InvalidFundSelectionException extends Exception {
    public InvalidFundSelectionException(String message) {
        super(message);
    }
}
class InvalidInvestorNameException extends Exception{
	public InvalidInvestorNameException(String message) {
		super(message);
	}
}

// Abstract class representing a mutual fund
abstract class MutualFund {
    public abstract double computeSalesLoad(double investmentAmount);
    public abstract double netAmountInvested(double investmentAmount);
    public abstract double purchasedShares(double investmentAmount, double navps);
}

interface ValidateUserData {
    boolean isValidInvestmentAmount(double investmentAmount) throws InvalidInvestmentAmountInputException;
    boolean isValidFundSelection(String fundType) throws InvalidFundSelectionException;
    boolean isValidInvestorName(String investorName) throws InvalidInvestorNameException;
}

// Concrete implementation of MutualFund for Save and Learn Balanced Fund (SALBF)
class SaveAndLearnBalancedFund extends MutualFund {
    private static final double SALES_LOAD_PERCENTAGE_1 = 0.02; // 1000 – 99,999.99
    private static final double SALES_LOAD_PERCENTAGE_2 = 0.015; // 100,000 – 499,999.99
    private static final double SALES_LOAD_PERCENTAGE_3 = 0.01; // 500,000 – 1,999,999.99
    private static final double SALES_LOAD_PERCENTAGE_4 = 0.005; // 2,000,000.00 and up

    @Override
    public double computeSalesLoad(double investmentAmount) {
        if (investmentAmount >= 1000 && investmentAmount < 100000) {
            return investmentAmount * SALES_LOAD_PERCENTAGE_1;
        } else if (investmentAmount >= 100000 && investmentAmount < 500000) {
            return investmentAmount * SALES_LOAD_PERCENTAGE_2;
        } else if (investmentAmount >= 500000 && investmentAmount < 2000000) {
            return investmentAmount * SALES_LOAD_PERCENTAGE_3;
        } else {
            return investmentAmount * SALES_LOAD_PERCENTAGE_4;
        }
    }

    @Override
    public double netAmountInvested(double investmentAmount) {
        return investmentAmount - computeSalesLoad(investmentAmount);
    }

    @Override
    public double purchasedShares(double investmentAmount, double navps) {
        return netAmountInvested(investmentAmount) / navps;
    }
}

// Concrete implementation of MutualFund for Save and Learn Equity Fund (SALEF)
class SaveAndLearnEquityFund extends MutualFund {
    private static final double SALES_LOAD_PERCENTAGE_1 = 0.02;
    private static final double SALES_LOAD_PERCENTAGE_2 = 0.015;
    private static final double SALES_LOAD_PERCENTAGE_3 = 0.01;
    private static final double SALES_LOAD_PERCENTAGE_4 = 0.005;

    @Override
    public double computeSalesLoad(double investmentAmount) {
        if (investmentAmount >= 1000 && investmentAmount < 100000) {
            return investmentAmount * SALES_LOAD_PERCENTAGE_1;
        } else if (investmentAmount >= 100000 && investmentAmount < 500000) {
            return investmentAmount * SALES_LOAD_PERCENTAGE_2;
        } else if (investmentAmount >= 500000 && investmentAmount < 2000000) {
            return investmentAmount * SALES_LOAD_PERCENTAGE_3;
        } else {
            return investmentAmount * SALES_LOAD_PERCENTAGE_4;
        }
    }

    @Override
    public double netAmountInvested(double investmentAmount) {
        return investmentAmount - computeSalesLoad(investmentAmount);
    }

    @Override
    public double purchasedShares(double investmentAmount, double navps) {
        return netAmountInvested(investmentAmount) / navps;
    }
}

// Concrete implementation of MutualFund for Save and Learn Fixed Income Fund (SALFIF)
class SaveAndLearnFixedIncomeFund extends MutualFund {
    private static final double SALES_LOAD_PERCENTAGE_1 = 0.02;
    private static final double SALES_LOAD_PERCENTAGE_2 = 0.015;
    private static final double SALES_LOAD_PERCENTAGE_3 = 0.01;
    private static final double SALES_LOAD_PERCENTAGE_4 = 0.005;

    @Override
    public double computeSalesLoad(double investmentAmount) {
        if (investmentAmount >= 1000 && investmentAmount < 100000) {
            return investmentAmount * SALES_LOAD_PERCENTAGE_1;
        } else if (investmentAmount >= 100000 && investmentAmount < 500000) {
            return investmentAmount * SALES_LOAD_PERCENTAGE_2;
        } else if (investmentAmount >= 500000 && investmentAmount < 2000000) {
            return investmentAmount * SALES_LOAD_PERCENTAGE_3;
        } else {
            return investmentAmount * SALES_LOAD_PERCENTAGE_4;
        }
    }

    @Override
    public double netAmountInvested(double investmentAmount) {
        return investmentAmount - computeSalesLoad(investmentAmount);
    }

    @Override
    public double purchasedShares(double investmentAmount, double navps) {
        return netAmountInvested(investmentAmount) / navps;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner userinput = new Scanner(System.in);
        DecimalFormat numberFormat = new DecimalFormat("#.00000");

        while (true) {
            System.out.print("\nEnter Investor Name: ");
            String investorName = userinput.nextLine();

            System.out.print("Enter Mutual Fund Type (SALEF, SALBF, or SALFIF): ");
            String fundType = userinput.nextLine().toUpperCase();

            System.out.print("Enter Investment Amount (Php): ");
            double investmentAmount = userinput.nextDouble();
            userinput.nextLine();  // Consume newline

            try {
                validateInput(investmentAmount, fundType, investorName);
                

                MutualFund mutualFund = getMutualFund(fundType);
                double navps = getNavps(fundType);

                double salesLoadAmount = mutualFund.computeSalesLoad(investmentAmount);
                double netAmountInvested = mutualFund.netAmountInvested(investmentAmount);
                double purchasedShares = mutualFund.purchasedShares(investmentAmount, navps);

                System.out.println("\nInvestor Name is " + investorName);
                System.out.println("Investment Fund Type is " + getFundFullName(fundType));
                System.out.println("Amount Invested is Php" + investmentAmount);
                System.out.println("NAVPS: Php" + navps + " (Updated as of June 05, 2024)");
                System.out.println("Sales Load Amount: Php" + salesLoadAmount);
                System.out.println("Net Amount Invested: Php" + netAmountInvested);
                System.out.println("Total Shares Bought: " + numberFormat.format(purchasedShares));

                System.out.print("\nDo you want to continue [Y/N]? ");
                String continueChoice = userinput.nextLine().toUpperCase();
                if (continueChoice.equals("N")) {
                    System.out.println("Thank You for Using The App.");
                    break;
                }
            } catch (InvalidInvestmentAmountInputException | InvalidFundSelectionException | InvalidInvestorNameException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        userinput.close();
    }

    private static void validateInput(double investmentAmount, String fundType, String investorName)
            throws InvalidInvestmentAmountInputException, InvalidFundSelectionException,InvalidInvestorNameException {
        if (investmentAmount <= 0) {
            throw new InvalidInvestmentAmountInputException("Invalid investment amount. Must be greater than zero.");
        }
        if (!fundType.matches("SALEF|SALBF|SALFIF")) {
            throw new InvalidFundSelectionException("Invalid fund type. Choose SALEF, SALBF, or SALFIF.");
        }
        if(investorName == null ||  investorName.matches(".*\\d.*")  ) {
        	throw new InvalidInvestorNameException("Invalid Name");
        }
    }
    
    private static MutualFund getMutualFund(String fundType) {
        switch (fundType) {
            case "SALEF":
                return new SaveAndLearnEquityFund();
            case "SALBF":
                return new SaveAndLearnBalancedFund();
            case "SALFIF":
                return new SaveAndLearnFixedIncomeFund();
            default:
                throw new IllegalArgumentException("Invalid fund type.");
        }
    }

    private static double getNavps(String fundType) {
        switch (fundType) {
            case "SALEF":
                return 4.5457;
            case "SALBF":
                return 2.4679;
            case "SALFIF":
                return 2.4444;
            default:
                throw new IllegalArgumentException("Invalid fund type.");
        }
    }

    private static String getFundFullName(String fundType) {
        switch (fundType) {
            case "SALEF":
                return "Save and Learn Equity Fund";
            case "SALBF":
                return "Save and Learn Balanced Fund";
            case "SALFIF":
                return "Save and Learn Fixed Income Fund";
            default:
                throw new IllegalArgumentException("Invalid fund type.");
        }
    }
}
