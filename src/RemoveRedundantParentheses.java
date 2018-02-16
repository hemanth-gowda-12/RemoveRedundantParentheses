import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class RemoveRedundantParentheses
{

    private static int getPrecedence(char c)
    {
        if (c == '+' || c == '-')
        {
            return 1;
        } else if (c == '/' || c == '*')
        {
            return 2;
        }
        return 0;
    }

    private static boolean isOperator(char c)
    {
        if (c == '+' || c == '-' || c == '/' || c == '*')
        {
            return true;
        }
        return false;
    }

    public static String getLeftOP(String expr, int index)
    {
        if (index == 0)
        {
            return null;
        } else if (expr.charAt(index - 1) == '+')
            return "+";
        else if (expr.charAt(index - 1) == '-')
            return "-";
        else if (expr.charAt(index - 1) == '*')
            return "*";
        else if (expr.charAt(index - 1) == '/')
            return "/";

        return null;
    }

    public static String getRightOP(String expr, int index)
    {
        if (index == expr.length() - 1)
        {
            return null;
        } else if (expr.charAt(index + 1) == '+')
            return "+";
        else if (expr.charAt(index + 1) == '-')
            return "-";
        else if (expr.charAt(index + 1) == '*')
            return "*";
        else if (expr.charAt(index + 1) == '/')
            return "/";

        return null;
    }

    private static String removeRedundantParentheses(String expr)
    {

        Stack<Integer> paranStack = new Stack<>();
        Stack<Integer> innerOperatorPrecedenceStack = new Stack<>();
        Set<Integer> toBeRemoved = new HashSet<>();

        for (int i = 0; i < expr.length(); i++)
        {
            if (expr.charAt(i) == '(')
            {
                paranStack.push(i);
                innerOperatorPrecedenceStack.push(Integer.MIN_VALUE);
            } else if (expr.charAt(i) == ')')
            {
                int openIndex = paranStack.pop();
                int closeIndex = i;

                Integer innerOpPrecedence = innerOperatorPrecedenceStack.pop();
                if (innerOpPrecedence == Integer.MIN_VALUE)
                {
                    toBeRemoved.add(openIndex);
                    toBeRemoved.add(closeIndex);
                    continue;
                } else
                {
                    innerOperatorPrecedenceStack.pop();
                }
                String L_OP = getLeftOP(expr, openIndex);
                String R_OP = getRightOP(expr, closeIndex);
                if (L_OP != null)
                {
                    if (getPrecedence(L_OP.charAt(0)) <= innerOpPrecedence)
                    {
                        toBeRemoved.add(openIndex);
                        toBeRemoved.add(closeIndex);
                    }
                    continue;
                }
                if (R_OP != null)
                {
                    if (getPrecedence(R_OP.charAt(0)) <= innerOpPrecedence)
                    {
                        toBeRemoved.add(openIndex);
                        toBeRemoved.add(closeIndex);
                    }
                }

            } else if (isOperator(expr.charAt(i)))
            {
                if (!innerOperatorPrecedenceStack.isEmpty() && !paranStack.isEmpty())
                    if (innerOperatorPrecedenceStack.peek().intValue() == Integer.MIN_VALUE)
                    {
                        innerOperatorPrecedenceStack.push(getPrecedence(expr.charAt(i)));
                    } else
                    {
                        Integer oldOpPrecedence = innerOperatorPrecedenceStack.pop();
                        innerOperatorPrecedenceStack.push(Integer.min(getPrecedence(expr.charAt(i)), oldOpPrecedence.intValue()));
                    }
            }

        }

        StringBuilder returnVal = new StringBuilder();
        for (int i = 0; i < expr.length(); i++)
        {
            if (!toBeRemoved.contains(i))
            {
                returnVal.append(expr.charAt(i));
            }
        }
        return returnVal.toString();
    }

    public static void main(String[] args)
    {
        /* Input examples:
                "x+(y+z)+(t+(v+w))"
                "1*(22+(3*(4+5)))"
                "23 + (3 / -5)"
        */
        if (args.length >= 1)
        {
            System.out.println("Processed output:" + removeRedundantParentheses(args[0].replaceAll("\\s+", "")));
        } else
        {
            System.err.println("No argument provided!! Please try again.");
        }
    }

}
