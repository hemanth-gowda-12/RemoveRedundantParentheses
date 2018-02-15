import java.util.HashSet;
import java.util.Set;

public class RemoveRedundantParentheses {


    public static void main(String[] args)
    {
        /* Input examples:
                "x+(y+z)+(t+(v+w))"
                "1*(22+(3*(4+5)))"
                "23 + (3 / -5)"
        */

        if(args.length>=1)
        {
            System.out.println("Processed output:"+ removeRedundantParentheses(args[0]));
        }
        else
        {
            System.err.println("No argument provided!! Please try again.");
        }
    }

    public static String getLeftOP(String expr, int index)
    {
        if(index==0)
        {
            return null;
        }
        else if(expr.charAt(index-1)=='+')
            return "+";
        else if(expr.charAt(index-1)=='-')
            return "-";
        else if(expr.charAt(index-1)=='*')
            return "*";
        else if(expr.charAt(index-1)=='/')
            return "/";

        return null;
    }

    public static String getRightOP(String expr, int index)
    {
        if(index==expr.length()-1)
        {
            return null;
        }
        else if(expr.charAt(index+1)=='+')
            return "+";
        else if(expr.charAt(index+1)=='-')
            return "-";
        else if(expr.charAt(index+1)=='*')
            return "*";
        else if(expr.charAt(index+1)=='/')
            return "/";

        return null;
    }

    private static String removeRedundantParentheses(String expr)
    {
        boolean redundantflag=true;
        int open=0;
        int offset=0;
        int close=0;
        Set<Integer> toBeRemoved=new HashSet<>();

        while(redundantflag)
        {
            redundantflag=false;
            boolean start=false;
            int count=0;

            for(int i=offset;i<expr.length();i++)
            {
                if(expr.charAt(i)=='(')
                {
                    if(count==0 && !start)
                    {
                        open=i;
                        offset=i+1;
                        start=true;
                    }
                    count++;

                }
                if(expr.charAt(i)==')')
                {
                        count--;

                        if(count==-1)
                        {
                            redundantflag=true;
                            offset = i + 1;
                            break;
                        }
                    if(count==0)
                    {
                        close=i;

                        String L_Op = getLeftOP(expr,open);
                        String R_Op = getRightOP(expr,close);
                        if (L_Op== null && R_Op == null)
                        {
                            toBeRemoved.add(open);
                            toBeRemoved.add(close);
                        }
                        else
                        {
                            int bracCount=0;
                            for(int x=open+1;x<close;x++)
                            {
                                if(expr.charAt(x)=='(')
                                {
                                    bracCount++;continue;
                                }
                                else if (expr.charAt(x)==')')
                                {
                                    bracCount--;continue;
                                }
                                if(bracCount==0)
                                {
                                    boolean predTest=false;

                                    if(isOperator(expr.charAt(x)))
                                    {
                                        if(L_Op!=null)
                                            predTest=getPrecedence(L_Op.charAt(0))<=getPrecedence(expr.charAt(x));
                                        if(R_Op!=null)
                                            if(!predTest)
                                                predTest=getPrecedence(R_Op.charAt(0)) <= getPrecedence(expr.charAt(x));


                                        if(predTest)
                                        {
                                            toBeRemoved.add(open);
                                            toBeRemoved.add(close);
                                            break;
                                        }
                                    }

                                }


                            }

                        }

                        redundantflag=true;
                        break;

                    }
                }
            }

        }


        StringBuilder returnVal=new StringBuilder();
        for(int i=0;i<expr.length();i++)
        {
            if(!toBeRemoved.contains(i))
            {
                returnVal.append(expr.charAt(i));
            }
        }
        return returnVal.toString();
    }

    private static int getPrecedence(char c)
    {
        if(c=='+'||c=='-')
        {
            return 1;
        }
        else if(c=='/'||c=='*')
        {
            return 2;
        }
        return 0;
    }

    private static boolean isOperator(char c)
    {
        if(c =='+' || c =='-' || c == '/' || c == '*')
        {
            return true;
        }
        return false;
    }

}
