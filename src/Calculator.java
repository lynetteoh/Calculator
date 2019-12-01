import java.util.Scanner;
import java.util.Stack;

public class Calculator {

	public static double calculate(String sum) {
		if( sum == null || sum.length() == 0 ) return 0;
		
		//temporary store result if encounter brackets
		Stack<Double> results = new Stack<Double>();
		
		double result = 0;
		char sign = '+';
		boolean isDecimal = false;
		for( int i = 0; i < sum.length(); i++ ) {
			char c = sum.charAt(i);
			if(Character.isDigit(c)) {
				if(isDecimal) {
					result = result + (double) ( c - '0' )/10;
				} else {
					result = result * 10 + (double) ( c - '0' );
				}

			} else if( c == '.') {
				isDecimal = true;
			} else {
				isDecimal = false;
			}
			
			if ( c == '(' && i != 0 ) {
				String newSum = "";
				char prevOp = sum.charAt(i - 2);
				if( prevOp == '+' || prevOp =='-' || prevOp  =='*' || prevOp == '/' ) {
					sign = prevOp;
				}
				i++;
				while(i < sum.length() && sum.charAt(i) != ')')
				{
					newSum += sum.charAt(i);
					i++;
				}
				
				//calculate the sum within the (...) recursively
				result = calculate(newSum);
				helper(result, results, sign);
				
			}
			
			if(  i + 1 == sum.length()  || c == '+' || c =='-' || c =='*' || c == '/' ) {
				if(sum.charAt(i) != ')')
				{
					helper(result, results, sign);
					sign = c;
					result = 0;
					
				}
				
			}
			
		}
		
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
		
	}

}
