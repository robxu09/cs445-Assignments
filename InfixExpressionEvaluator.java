package cs445.a2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

/**
 * This class uses two stacks to evaluate an infix arithmetic expression from an
 * InputStream. It should not create a full postfix expression along the way; it
 * should convert and evaluate in a pipelined fashion, in a single pass.
 */
public class InfixExpressionEvaluator {
    // Tokenizer to break up our input into tokens
    StreamTokenizer tokenizer;

    // Stacks for operators (for converting to postfix) and operands (for
    // evaluating)
    StackInterface<Character> operatorStack;
    StackInterface<Double> operandStack;

    /**
     * Initializes the evaluator to read an infix expression from an input
     * stream.
     * @param input the input stream from which to read the expression
     */
    public InfixExpressionEvaluator(InputStream input) {
        // Initialize the tokenizer to read from the given InputStream
        tokenizer = new StreamTokenizer(new BufferedReader(
                        new InputStreamReader(input)));

        // StreamTokenizer likes to consider - and / to have special meaning.
        // Tell it that these are regular characters, so that they can be parsed
        // as operators
        tokenizer.ordinaryChar('-');
        tokenizer.ordinaryChar('/');

        // Allow the tokenizer to recognize end-of-line, which marks the end of
        // the expression
        tokenizer.eolIsSignificant(true);

        // Initialize the stacks
        operatorStack = new ArrayStack<Character>();
        operandStack = new ArrayStack<Double>();
    }

    /**
     * Parses and evaluates the expression read from the provided input stream,
     * then returns the resulting value
     * @return the value of the infix expression that was parsed
     */
    public Double evaluate() throws InvalidExpressionException {
        // Get the first token. If an IO exception occurs, replace it with a
        // runtime exception, causing an immediate crash.
        try {
            tokenizer.nextToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Continue processing tokens until we find end-of-line
        while (tokenizer.ttype != StreamTokenizer.TT_EOL) {
            // Consider possible token types
            switch (tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    // If the token is a number, process it as a double-valued
                    // operand
                    handleOperand((double)tokenizer.nval);
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                case '^':
                    // If the token is any of the above characters, process it
                    // is an operator
                    handleOperator((char)tokenizer.ttype);
                    break;
                case '(':
                case '{':
                    // If the token is open bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    handleOpenBracket((char)tokenizer.ttype);
                    break;
                case ')':
                case '}':
                    // If the token is close bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    handleCloseBracket((char)tokenizer.ttype);
                    break;
                case StreamTokenizer.TT_WORD:
                    // If the token is a "word", throw an expression error
                    throw new InvalidExpressionException("Unrecognized symbol: " +
                                    tokenizer.sval);
                default:
                    // If the token is any other type or value, throw an
                    // expression error
                    throw new InvalidExpressionException("Unrecognized symbol: " +
                                    String.valueOf((char)tokenizer.ttype));
            }

            // Read the next token, again converting any potential IO exception
            try {
                tokenizer.nextToken();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Almost done now, but we may have to process remaining operators in
        // the operators stack
        handleRemainingOperators();

        // Return the result of the evaluation
        // TODO: Fix this return statement
        return operandStack.pop();
    }

    /**
     * This method is called when the evaluator encounters an operand. It
     * manipulates operatorStack and/or operandStack to process the operand
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param operand the operand token that was encountered
     */
    void handleOperand(double operand) {
        // TODO: Complete this method
        //push to operand stack
        operandStack.push(operand);
    }

    /**
     * This method is called when the evaluator encounters an operator. It
     * manipulates operatorStack and/or operandStack to process the operator
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param operator the operator token that was encountered
     */
    void handleOperator(char operator) {
        // TODO: Complete this method
        //peek at operator stack
        //do Math if higher or equal precedence
        //push to operator stack
        char operatorBefore;
        boolean gottaMath=true;

        while(gottaMath){
            if(!operatorStack.isEmpty()){
                operatorBefore = operatorStack.peek();
                switch(operatorBefore){
                case '^':
                    gottaMath = true;
                    break;
                case '/':
                case '*':
                    if(operator == '^'){
                        gottaMath = false;
                    }else{
                        gottaMath = true;
                    }
                    break;
                case '-':
                case '+':
                    if(operator=='-'||operator=='+'){
                        gottaMath = true;
                    }else{
                        gottaMath = false;
                    }
                    break;
                case '(':
                case '{':
                default:
                    gottaMath = false;
                    break;
                }
            }else{
                gottaMath=false;
            }
            if(gottaMath){
                doMath();
            }
        }

        operatorStack.push(operator);
        
    }

    /**
     * This method is called when the evaluator encounters an open bracket. It
     * manipulates operatorStack and/or operandStack to process the open bracket
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param openBracket the open bracket token that was encountered
     */
    void handleOpenBracket(char openBracket) {
        // TODO: Complete this method
        //push it to operator stack
        operatorStack.push(openBracket);        
    }

    /**
     * This method is called when the evaluator encounters a close bracket. It
     * manipulates operatorStack and/or operandStack to process the close
     * bracket according to the Infix-to-Postfix and Postfix-evaluation
     * algorithms.
     * @param closeBracket the close bracket token that was encountered
     */
    void handleCloseBracket(char closeBracket) {
        // TODO: Complete this method
        //Math until open bracket
        boolean foundOpen = false;
        char counterPart='a';
        char noMyPartner='a';

        if(closeBracket==')'){
            counterPart = '(';
            noMyPartner = '{';
        }else{
            counterPart = '{';
            noMyPartner = '(';
        }
        while(!foundOpen&&!operatorStack.isEmpty()){
            if(operatorStack.peek()==noMyPartner){
                throw new InvalidExpressionException("You have mismatched parenthesis.");
            }
            if(operatorStack.peek()==counterPart){
            	operatorStack.pop();
                foundOpen=true;
            }else{
            	doMath();
            }
            
        }
        if(!foundOpen&&operatorStack.isEmpty()){
            //throw error. didn't find any open.
            throw new InvalidExpressionException("You need an open parenthesis before a close.");
        }
    }

    /**
     * This method is called when the evaluator encounters the end of an
     * expression. It manipulates operatorStack and/or operandStack to process
     * the operators that remain on the stack, according to the Infix-to-Postfix
     * and Postfix-evaluation algorithms.
     */
    void handleRemainingOperators() {
        // TODO: Complete this method
        //while operator stack not empty, do Math
        while(!operatorStack.isEmpty()){
            doMath();
        }
    }

    //make a math function
        //pop operand and save as first operand
        //pop operand and save as second operand
        //pop operator
        //do second operate first
        //push answer to operand stack
    void doMath(){
        double second;
        double first;
        double answer;
        char op = operatorStack.pop();

        //big switch function that checks op and does math and push answer.
        switch(op){
            case '+':
            	if(!operandStack.isEmpty()){
            		second = operandStack.pop();
            	}else{
            		throw new InvalidExpressionException("Your operators do not match the number of operands."
            			+ "\nOperands and operators should alternate, with operand first (excluding parenthesis)."
            			+ "\nAlso, operators should not be right after an opening parenthesis nor right before a closing one.");
            	}
                if(!operandStack.isEmpty()){
                	first = operandStack.pop();
                }else{
                	throw new InvalidExpressionException("Your operators do not match the number of operands."
            			+ "\nOperands and operators should alternate, with operand first (excluding parenthesis)."
            			+ "\nAlso, operators should not be right after an opening parenthesis nor right before a closing one.");
                }
                
                
                answer = first + second;
                operandStack.push(answer);
                break;
            case '-':
                if(!operandStack.isEmpty()){
            		second = operandStack.pop();
            	}else{
            		throw new InvalidExpressionException("Your operators do not match the number of operands."
            			+ "\nOperands and operators should alternate, with operand first (excluding parenthesis)."
            			+ "\nAlso, operators should not be right after an opening parenthesis nor right before a closing one.");
            	}
                if(!operandStack.isEmpty()){
                	first = operandStack.pop();
                }else{
                	throw new InvalidExpressionException("Your operators do not match the number of operands."
            			+ "\nOperands and operators should alternate, with operand first (excluding parenthesis)."
            			+ "\nAlso, operators should not be right after an opening parenthesis nor right before a closing one.");
                }
                answer = first - second;
                operandStack.push(answer);
                break;
            case '*':
                if(!operandStack.isEmpty()){
            		second = operandStack.pop();
            	}else{
            		throw new InvalidExpressionException("Your operators do not match the number of operands."
            			+ "\nOperands and operators should alternate, with operand first (excluding parenthesis)."
            			+ "\nAlso, operators should not be right after an opening parenthesis nor right before a closing one.");
            	}
                if(!operandStack.isEmpty()){
                	first = operandStack.pop();
                }else{
                	throw new InvalidExpressionException("Your operators do not match the number of operands."
            			+ "\nOperands and operators should alternate, with operand first (excluding parenthesis)."
            			+ "\nAlso, operators should not be right after an opening parenthesis nor right before a closing one.");
                }
                answer = first * second;
                operandStack.push(answer);
                break;
            case '/':
                if(!operandStack.isEmpty()){
            		second = operandStack.pop();
            	}else{
            		throw new InvalidExpressionException("Your operators do not match the number of operands."
            			+ "\nOperands and operators should alternate, with operand first (excluding parenthesis)."
            			+ "\nAlso, operators should not be right after an opening parenthesis nor right before a closing one.");
            	}
                if(!operandStack.isEmpty()){
                	first = operandStack.pop();
                }else{
                	throw new InvalidExpressionException("Your operators do not match the number of operands."
            			+ "\nOperands and operators should alternate, with operand first (excluding parenthesis)."
            			+ "\nAlso, operators should not be right after an opening parenthesis nor right before a closing one.");
                }
                if(second == 0){
                    throw new InvalidExpressionException("Don't divide by zero.");
                }
                answer = first / second;
                operandStack.push(answer);
                break;
            case '^':
                if(!operandStack.isEmpty()){
            		second = operandStack.pop();
            	}else{
            		throw new InvalidExpressionException("Your operators do not match the number of operands."
            			+ "\nOperands and operators should alternate, with operand first (excluding parenthesis)."
            			+ "\nAlso, operators should not be right after an opening parenthesis nor right before a closing one.");
            	}
                if(!operandStack.isEmpty()){
                	first = operandStack.pop();
                }else{
                	throw new InvalidExpressionException("Your operators do not match the number of operands."
            			+ "\nOperands and operators should alternate, with operand first (excluding parenthesis)."
            			+ "\nAlso, operators should not be right after an opening parenthesis nor right before a closing one.");
                }
                answer = Math.pow(first, second);
                operandStack.push(answer);
                break;
            case '(':
            case '{':
            	throw new InvalidExpressionException("Don't have an open parenthesis without a closer.");
            default:
                break;
        }
    }

    /**
     * Creates an InfixExpressionEvaluator object to read from System.in, then
     * evaluates its input and prints the result.
     * @param args not used
     */
    public static void main(String[] args) {
        System.out.println("Infix expression:");
        InfixExpressionEvaluator evaluator =
                        new InfixExpressionEvaluator(System.in);
        Double value = null;
        try {
            value = evaluator.evaluate();
        } catch (InvalidExpressionException e) {
            System.out.println("Invalid expression: " + e.getMessage());
        }
        if (value != null) {
            System.out.println(value);
        }
    }

}

