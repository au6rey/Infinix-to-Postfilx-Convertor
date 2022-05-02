import java.util.Stack;

class Convertor {

	public static String convertToPostfix(String expression) {
		StringBuilder builder = new StringBuilder();
		Stack<Character> stack = new Stack<Character>();
		stack.push('$');
		String[] stripped = expression.split(" ");

		for (int i = 0; i < stripped.length; i++) {
			if (stripped[i].length() > 1) {
				builder.append(stripped[i] + " ");
			} else {
				char c = stripped[i].charAt(0);
				if (Character.isLetterOrDigit(c)) {
					builder.append(c + " ");
				} else if (c == '(') {
					stack.push(c);
				} else if (c == ')') {
					while (stack.peek() != '$' && stack.peek() != '(') {
						builder.append(stack.pop() + " ");
					}
					stack.pop();
				} else {
					if (precedence(c) > precedence(stack.peek())) {
						stack.push(c);
					} else {
						while (stack.peek() != '$' && precedence(c) <= precedence(stack.peek())) {
							builder.append(stack.pop() + " ");
						}
						stack.push(c);
					}
				}
			}
		}

		while (stack.peek() != '$') {
			builder.append(stack.pop() + " ");
		}

		stack.clear();
		return builder.toString();
	}

	public static double evaluatePostfix(String expression) {

		Stack<Double> stack = new Stack<Double>();
		String[] stripped = expression.strip().split(" ");
		for (int i = 0; i < stripped.length; i++) {
			String current = stripped[i];
			if (current.length() == 1 && !Character.isDigit(current.charAt(0))) {
				double next = stack.pop();
				double prev = stack.pop();
				char operator = current.charAt(0);
				double result = performOperations(prev, next, operator);
				stack.push(result);
			} else {
				stack.push(Double.parseDouble(current));
			}
		}

		return stack.peek();
	}

	private static double performOperations(double prev, double next, char operator) {
		if (operator == '*') {
			prev *= next;
		}
		if (operator == '/') {
			prev /= next;
		}
		if (operator == '-') {
			prev -= next;
		}
		if (operator == '+') {
			prev += next;
		}
		return prev;
	}

	private static int precedence(char ch) {
		if (ch == '+' || ch == '-') {
			return 1;
		} else if (ch == '*' || ch == '/') {
			return 2;
		} else {
			return 0;
		}
	}
}
