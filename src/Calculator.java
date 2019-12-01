import java.util.Scanner;
import java.util.Stack;

public class Calculator {

	public static double calculate(String sum) {
		
		if( sum == null || sum.length() == 0 ) return 0;
		
		//temporary store result if encounter brackets
		Stack<Double> results = new Stack<Double>();
		
		double result = 0;

		// last seen sign
		char sign = '+';
		boolean isDecimal = false;

		for( int i = 0; i < sum.length(); i++ ) {
			char c = sum.charAt(i);
			if(Character.isDigit(c)) {
				
				
				if(isDecimal) {
					// take the new digit and divide by 10 to turn it into decimal
					result = result + (double) ( c - '0' )/10;
				} else {
					// multiply result by 10 before adding the new digit
					result = result * 10 + (double) ( c - '0' );
				}

			} else if( c == '.') {
				isDecimal = true;
			} else {
				/* only reset this flag if we encounter space, parenthesis or maths operation as
				as we might have 1.999999... 
				*/
				isDecimal = false;
			}
			
			//encounter open bracket
			if ( c == '(' && i != 0 ) {
				String newSum = "";

				// record previous mathematics operation before this numbeer
				char prevOp = sum.charAt(i - 2);
				if( prevOp == '+' || prevOp =='-' || prevOp  =='*' || prevOp == '/' ) {
					sign = prevOp;
				}
				
				// discard the bracket
				i++;

				// take everything within the parenthesis ( .... )
				while(i < sum.length() && sum.charAt(i) != ')')
				{
					newSum += sum.charAt(i);
					i++;
				}
				
				//calculate the sum within the (...) recursively
				result = calculate(newSum);
				helper(result, results, sign);
				
			}
			
			// add the result into the stack 
			if(  i + 1 == sum.length()  || c == '+' || c =='-' || c =='*' || c == '/' ) {

				// do not need to add the result into the stack if the last character is ')' 
				// as this has been added during the previous call
				if(sum.charAt(i) != ')')
				{
					helper(result, results, sign);
					sign = c;
					result = 0;
					
				}
				
			}
			
		}
		
		// sum everything in the stack to get the final sum 
		double finalresult = 0.0;
		while(!results.isEmpty()) {
			double cur = results.pop();
			finalresult += cur;
		}
		
		return finalresult;
		
	}

	private static void helper(double result, Stack<Double> results, char sign) {
		switch(sign) {
			case '+':
				results.push(result);
				break;
			case '-':
				results.push(-result);
				break;
			case '*':
				results.push(results.pop() * result);
				break;
			case '/':
				results.push(results.pop()/result);
				break;	
		}	
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter a maths equation: ");
		String equation = input.nextLine();
		double result = calculate(equation);
		System.out.println("Total is " + result);
		input.close();
	}

}
