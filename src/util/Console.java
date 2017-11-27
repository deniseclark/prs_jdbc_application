package util;
import java.util.Scanner;

// ****************************************************************************
// ***                                                                      ***
// *** Class Name:  Console                                                 ***
// ***                                                                      ***
// *** Description: The Console Class is a Class that is Used to Obtain     ***
// ***              a Specified Type of Input from the Console via a Prompt.*** 
// ***              The Following Static Methods are Available:             ***
// ***                                                                      ***
// ***              1.)  getInt    (Version #1 - Any Valid Integer)         ***
// ***              2.)  getInt    (Version #2 - MIN and MAX # Digits)      ***
// ***              3.)  getInt    (Version #3 - Specific # of Digits)      ***
// ***                                                                      ***
// ***              4.)  getString  (Version #1 - Any Valid String)         ***
// ***              5.)  getString  (Version #2 - MIN and MAX # Characters) ***
// ***              6.)  getString  (Version #3 - Specific # of Characters) ***
// ***                                                                      ***
// ***              7.)  getLong    (Version #1 - Any Valid Long)           ***
// ***              8.)  getLong    (Version #2 - MIN and MAX # Digits)     ***
// ***              9.)  getLong    (Version #3 - Specific # of Digits)     ***
// ***                                                                      ***
// ***              10.) getDouble  (Version #1 - Any Valid Long)           ***
// ***              11.) getDouble  (Version #2 - MIN and MAX # Digits)     ***
// ***                                                                      ***
// ***              12.) getYNFlag    (Value Y or N - Return Y/N)           ***
// ***              13.) getYNBoolean (Value Y or N - Return true or false) ***
// ***              14.) getBoolean   (Value True or False)                 ***
// ***                                                                      ***
// ***              15.) getEmail     (String Must Contain @ and .)         ***
// ***                                                                      ***
// ***              16.) getPassword (See Method for Security Policy)       ***
// ***                                                                      ***
// ****************************************************************************
public class Console {

	 static Scanner sc = new Scanner(System.in);
	
	// **************************************
	// *** Prompt for a Integer Value ***
	// **************************************
	public static int getInt(String prompt) {
		int i = 0;
		boolean isValid = false;
		while (!isValid) {
			System.out.print(prompt);
			if (sc.hasNextInt()) {
				i = sc.nextInt();
				isValid = true;
			} else {
				System.out.println("%%% Invalid Integer Value - Try Again");
			}
			sc.nextLine(); // discard any other data entered on the line
		}
		return i;
	}

	// **************************************************************
	// *** Prompt for a Integer Value within a MIN and MAX Value ***
	// **************************************************************
	public static int getInt(String prompt, int min, int max) {
		int i = 0;
		boolean isValid = false;
		while (!isValid) {
			i = getInt(prompt);
			if (i < min) {
				System.out.println("%%% Value Must Be Greater than " + min);
			} else if (i > max) {
				System.out.println("%%% Value Must be Less than " + max);
			} else {
				isValid = true;
			}
		}
		return i;
	}

	// ******************************************************************************
	// *** Prompt for a Integer Value a Specified Number of Digits (Phone
	// Number) ***
	// *****************************************************************************
	public static int getInt(String prompt, int numDigits) {
		int i = 0;
		boolean isValid = false;
		while (!isValid) {
			System.out.print(prompt);
			if (sc.hasNextInt()) {
				i = sc.nextInt();
				int length = String.valueOf(i).length();
				if (length == numDigits)
					isValid = true;
				else
					System.out.println("%%% " + numDigits + " Numbers Must be Entered");
			} else {
				System.out.println("%%% Invalid Value. Try Again");
			}
			sc.nextLine(); // discard any other data entered on the line
		}
		return i;
	}

	// *********************************
	// *** Prompt for String Value ***
	// *********************************
	public static String getString(String prompt) {		
		String string = "";
		boolean isValid = false;
		while (!isValid) {
			System.out.print(prompt);
			if (sc.hasNext()) {
				string = sc.nextLine();
				isValid = true;
			} else {
				System.out.println("%%% Invalid Entry. Try Again.");
			}
			//sc.nextLine();
		}
		return string;
	}
	
	public static String getStringAllowNull(String prompt) {		
		String string = "";
        System.out.print(prompt);
        string = sc.nextLine();
        return string;
    }
		

	// *********************************************************************
	// *** Prompt for String Value that Contains a Number of Characters ***
	// *** Between the MIN and MAX Number of Characters ***
	// *********************************************************************
	public static String getString(String prompt, int min, int max) {
		String string = "";
		boolean isValid = false;
		while (!isValid) {
			System.out.print(prompt);
			if (sc.hasNext()) {
				string = sc.nextLine();
				int length = String.valueOf(string).length();
				if (length < min) {
					System.out.println("%%% Number of Characters Must be Greater than " + min);
				} else if (length > max) {
					System.out.println("%%% Number of Characters Must be Less than " + max);
				} else
					isValid = true;
			} else {
				System.out.println("%%% Invalid Entry. Try Again.");
			}
			//sc.nextLine();
		}
		return string;
	}

	// **************************************************************
	// *** Prompt for String Value of that is one of two Value    ***
	// **************************************************************
    public static String getString(String prompt, String value1, String value2) {
		String string = "";
		boolean isValid = false;
		while (!isValid) {
            System.out.print(prompt);
            string = sc.nextLine();
            if (string.isEmpty()) {
                System.out.println("%%% Please Enter Either "+value1+" or "+value2);
            } else {
            	if ((string.equalsIgnoreCase(value1)) || (string.equalsIgnoreCase(value2))) {
                    isValid = true;
                } else {
                    System.out.println("%%% Please Enter Either "+value1+" or "+value2);
                }
            }
        }
		return string;
    }
    
	// ********************************************************************
	// *** Prompt for String Value of a Specific Number of Characters   ***
	// ********************************************************************
	public static String getString(String prompt, int numChars) {
		String string = "";
		boolean isValid = false;
		while (!isValid) {
			System.out.print(prompt);
			if (sc.hasNext()) {
				string = sc.nextLine();
				int length = String.valueOf(string).length();
				if (length == numChars)
					isValid = true;
				else
					System.out.println("%%% " + numChars + " Characters Must be Entered");
			} else {
				System.out.println("%%% Invalid Entry. Try Again");
			}
			//sc.nextLine();
		}
		return string;
	}

	// *******************************
	// *** Prompt for a Long Value ***
	// *******************************
	public static long getLong(String prompt) {
		long l = 0;
		boolean isValid = false;
		while (!isValid) {
			System.out.print(prompt);
			if (sc.hasNextLong()) {
				l = sc.nextLong();
				isValid = true;
			} else {
				System.out.println("%%% Invalid Value. Try Again");
			}
			sc.nextLine(); // discard any other data entered on the line
		}
		return l;
	}

	// **********************************************************
	// *** Prompt for a Long Value within a MIN and MAX Value ***
	// **********************************************************
	public static long getLong(String prompt, int min, int max) {
		long l = 0;
		boolean isValid = false;
		while (!isValid) {
			l = getLong(prompt);
			if (l < min) {
				System.out.println("%%% Value Must be Greater than " + min + ".");
			} else if (l > max) {
				System.out.println("%%% Value Must be Less than " + max + ".");
			} else {
				isValid = true;
			}
			sc.nextLine(); // discard any other data entered on the line
		}
		return l;
	}

	// ***************************************************************************
	// *** Prompt for a Long Value a Specified Number of Digits (Phone Number) ***
	// ***************************************************************************
	public static long getLong(String prompt, int numDigits) {
		long i = 0;
		boolean isValid = false;
		while (!isValid) {
			System.out.print(prompt);
			if (sc.hasNextLong()) {
				i = sc.nextLong();
				int length = String.valueOf(i).length();
				if (length == numDigits)
					isValid = true;
				else
					System.out.println("%%% " + numDigits + " Numbers Must be Entered");
			} else {
				System.out.println("%%% Invalid Value. Please Re-Enter");
			}
			sc.nextLine(); // discard any other data entered on the line
		}
		return i;
	}

	// ***********************************
	// *** Prompt for a Double Value ***
	// ***********************************
	public static double getDouble(String prompt) {
		double d = 0.0;
		boolean isValid = false;
		while (!isValid) {
			System.out.print(prompt);
			if (sc.hasNextDouble()) {
				d = sc.nextDouble();
				isValid = true;
			} else {
				System.out.println("%%% Invalid Decimal Number. Try again.");
			}
			sc.nextLine(); // discard any other data entered on the line
		}
		return d;
	}

	// **************************************************************
	// *** Prompt for a Double Value within a MIN and MAX Value ***
	// **************************************************************
	public static double getDouble(String prompt, double min, double max) {
		double d = 0.0;
		boolean isValid = false;
		while (!isValid) {
			d = getDouble(prompt);
			if (d < min) {
				System.out.println("%%% Number Must be Greater than " + min);
			} else if (d > max) {
				System.out.println("%%% Number Must be Less than " + max);
			} else {
				isValid = true;
			}
		}
		return d;
	}

	// ****************************************
	// *** Prompt for a Y/N Flag (String) ***
	// ****************************************
	public static String getYNFlag(String prompt) {
		String flag = "";
		boolean isValid = false;
		while (!isValid) {
			System.out.print(prompt);
			if (sc.hasNext()) {
				flag = sc.next();
				if ((flag.equalsIgnoreCase("Y")) || (flag.equalsIgnoreCase("N")))
					isValid = true;
				else
				   System.out.println("%%% Value Must be Either Y or N - Try Again");
			} else {
				System.out.println("%%% Value Must be Either Y or N - Try Again");
			}
			sc.nextLine();
		}
		return flag.toUpperCase();
	}

	// ***************************************************************
	// *** Prompt for a Y/N Flag - Return a Boolean Y=True N=False ***
	// ***************************************************************
	public static Boolean getYNBoolean(String prompt) {
		String flag = "";
		Boolean returnFlag;
		boolean isValid = false;
		while (!isValid) {
			System.out.print(prompt);
			if (sc.hasNext()) {
				flag = sc.next();
				if ((flag.equalsIgnoreCase("Y")) || (flag.equalsIgnoreCase("N")))
					isValid = true;
				else
					System.out.println("%%% Value Must be Either Y or N - Try Again");
			} else {
				System.out.println("%%% Value Must be Either Y or N - Try Again");
			}
			sc.nextLine();
		}
		if (flag.equalsIgnoreCase("Y"))
			returnFlag = true;
		else
			returnFlag = false;
		return returnFlag;
	}

	// ***************************************************************
	// *** Prompt for a Boolean - Return a Boolean true or false ***
	// ***************************************************************
	public static Boolean getBoolean(String prompt) {
		String string = "";
		boolean outValue = false;  // initialization required 
		boolean isValid = false;
		while (!isValid) {
			System.out.print(prompt);
			if (sc.hasNext()) {
				string = sc.next();
				if (string.equalsIgnoreCase("true")) {
					outValue = true;
					isValid = true;

				} else if (string.equalsIgnoreCase("false")) {
					outValue = false;
					isValid = true;
				}
			} else {
				System.out.println("%%% Value Must be Either True or False - Try Again");
			}
			sc.nextLine();
		}
		return outValue;  // Only when isValid is True
	}

	// **********************************************************
	// *** Prompt for an Email Address (Contains and @ and .) ***
	// **********************************************************
	public static String getEmail(String prompt) {
		String email = "";
		boolean isValid = false;
		while (!isValid) {
			System.out.print(prompt);
			if (sc.hasNext()) {
				email = sc.next();
				if (email.contains("@") && email.contains("."))
					isValid = true;
				else
					System.out.println("%%% Invalid Email Address Format");
			} else {
				System.out.println("%%% Invalid Entry. Try again");
			}
			sc.nextLine();
		}
		return email;
	}

	// *************************************************************
	// *** Get Password (Verify for Security Policy)             ***
	// *** 1.) MIN to MAX Characters                             ***
	// *** 2.) At Least 1 Upper case Character                   ***
	// *** 3.) At Least 1 Lower case Character                   ***
	// *** 4.) At Least 1 Special Character                      *** 
	// *** 5.) Not the Same as the UserName (Required as Input)  ***
	// *************************************************************
	public static String getPassword(String prompt, String userName, int min, int max) {
		String password = "";
		String upperCaseChars = "(.*[A-Z].*)";
		String lowerCaseChars = "(.*[a-z].*)";
		String numbers = "(.*[0-9].*)";
		String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
		boolean isValid = false;
		while (!isValid) {
			System.out.print(prompt);
			if (sc.hasNext()) {
				password = sc.next();
				if ((password.length() > max) || (password.length() < min)) {
					System.out.println("%%% Passwords Must be Between " + min + " and " + max + " Characters");
				} else if (password.indexOf(userName) > -1) {
					System.out.println("%%% Passwords Cannot be the Same as the UserName");
				} else if (!password.matches(upperCaseChars)) {
					System.out.println("%%% Passwords Must Contains at Least 1 Uppercase Character");
				} else if (!password.matches(lowerCaseChars)) {
					System.out.println("%%% Passwords Must Contains at Least 1 Lowercase Character");
				} else if (!password.matches(numbers)) {
					System.out.println("%%% Passwords Must Contains at Least 1 Number");
				} else if (!password.matches(specialChars)) {
					System.out.println("%%% Passwords Must Contains at Least 1 Special Character");
				} else {
					isValid = true;
				}
			} else {
				System.out.println("%%% Invalid Entry. Try Again.");
			}
			sc.nextLine();
		}
		return password;
	}
}