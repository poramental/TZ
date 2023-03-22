package org.example;


import java.util.*;

public class Main {

    static Scanner in = new Scanner(System.in);

    static char[] operations = {'+','-','*','/'};

    private static boolean isRoman(String value){
        if(value.contains("I") || value.contains("X") || value.contains("V")) return true;
        else return false;
    }

    private static int toArabic(String value)throws Exception{
        if(value.equals("I")) return 1;
        if(value.equals("II")) return 2;
        if(value.equals("III")) return 3;
        if(value.equals("IV")) return 4;
        if(value.equals("V")) return 5;
        if(value.equals("VI")) return 6;
        if(value.equals("VII")) return 7;
        if(value.equals("VIII")) return 8;
        if(value.equals("IX")) return 9;
        if(value.equals("X")) return 10;
        throw new Exception("format does not match");
    }

    private static String toRoman(int num){
        var keys = new String[] { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
        var vals = new int[] { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };

        String ret = "";
        int ind = 0;

        while(ind < keys.length)
        {
            while(num >= vals[ind])
            {
                var d = num / vals[ind];
                num = num % vals[ind];
                for(int i=0; i<d; i++)
                    ret += keys[ind];
            }
            ind++;
        }

        return ret;
    }

    private static HashMap<String,String> parseExpression(String expression) throws Exception{

        for(char operation : operations){
            int index = expression.indexOf(operation);
            if(index != -1){
                HashMap exprMap = new HashMap<>();
                String exprOperation  = String.valueOf(expression.charAt(index));
                if(exprOperation.equals("+"))   // зарезервированный символ в регулярках
                    exprOperation = "\\+";  // экранируем
                if(exprOperation.equals("*"))
                    exprOperation = "\\*";
                String[] operands = expression.split( exprOperation); // получаем операнды
                if(operands.length > 2)
                    throw new Exception("format does not match");
                exprMap.put("operation",exprOperation);
                exprMap.put("firstOperand",operands[0].replaceAll(" ",""));
                exprMap.put("secondOperand",operands[1].replaceAll(" ",""));
                return exprMap;
            }
        }
        throw new Exception("format does not match");
    }

    private static String calc(String input) throws Exception{
        HashMap<String,String> exprMap =  parseExpression(input);
        String operation = exprMap.get("operation");
        String firstOperand = exprMap.get("firstOperand");
        String secondOperand = exprMap.get("secondOperand");

        if(isRoman(firstOperand) && isRoman(secondOperand)){
            if(toArabic(firstOperand) > 10 || toArabic(firstOperand) < 0
                    || toArabic(secondOperand) > 10 || toArabic(secondOperand) < 0)
                throw new Exception("format does not match");

            if(operation.equals("\\+")){
               return toRoman(toArabic(firstOperand) + toArabic(secondOperand));
            }
            if(operation.equals("-")){
                return toRoman(toArabic(firstOperand) - toArabic(secondOperand));
            }
            if(operation.equals("\\*")){
                return toRoman(toArabic(firstOperand) * toArabic(secondOperand));
            }
            if(operation.equals("/")){

                return toRoman((int)(toArabic(firstOperand) / toArabic(secondOperand)));
            }
        }
        if(isRoman(firstOperand) && !isRoman(secondOperand))
            throw new Exception("format does not match");
        if(!isRoman(firstOperand) && isRoman(secondOperand))
            throw new Exception("format does not match");

        try {
            if (operation.equals("\\+")) {
                return String.valueOf(Integer.parseInt(firstOperand)+Integer.parseInt(secondOperand));
            }
            if (operation.equals("-")) {
                return String.valueOf(Integer.parseInt(firstOperand) - Integer.parseInt(secondOperand));
            }
            if (operation.equals("\\*")) {
                return String.valueOf(Integer.parseInt(firstOperand) * Integer.parseInt(secondOperand));
            }
            if (operation.equals("/")) {
                return String.valueOf(Integer.parseInt(firstOperand) / Integer.parseInt(secondOperand));
            }
        }catch (NumberFormatException e){
            throw new Exception("format does not match");
        }
        throw new Exception("format does not match");
    }


    public static void main(String[] args) throws Exception{
        String expression;
        expression = in.nextLine();
        System.out.println(calc(expression));

    }



}