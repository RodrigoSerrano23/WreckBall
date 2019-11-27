/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.util.ArrayList;

/**
 *
 * @author ClownToy
 */
public class CodigoLMS {
    private String[] cuartetos;
    private String res;
    
    public CodigoLMS(String a){
        cuartetos=a.split("\n");
        res="vmthread main(){\nDATA8 boolRegister\nDATA8 eightRegister\nDATA16 sixteenRegister\nDATA32 timeRegister\nDATA32 timeCounter\nDATA32 thirtytwoRegister\n";
    }
    
    public String lms(){
        for (String cuarteto : cuartetos) {
            res = res + traslacion(cuarteto);
        }
        res=res+"}";
        return res;
    }
    
    private String traslacion(String cuarteto){
        String resultado="";
        if(cuarteto.substring(0,1).equals("(") && cuarteto.substring(cuarteto.length()-1,cuarteto.length()).equals(")")){
            resultado=comprobarCuarteto(cuarteto.substring(1,cuarteto.length()-1));
        }else{
            resultado=cuarteto+"\n";
        }
        return resultado;    
    }
        
    private String comprobarCuarteto(String cuarteto){
        try{
        String[] arreglo=cuarteto.split(",");
        switch(arreglo[0]){
            case "int": return "DATA16 "+arreglo[3]+"\n";
            case "bool": return "DATA8 "+arreglo[3]+"\n";
            case "=":return igual(arreglo[1],arreglo[3]);
            case "+":return "ADD16("+arreglo[1]+","+arreglo[2]+",sixteenRegister)\n";
            case "-":return "SUB16("+arreglo[1]+","+arreglo[2]+",sixteenRegister)\n";
            case "*":return "MUL16("+arreglo[1]+","+arreglo[2]+",sixteenRegister)\n";
            case "/":return "DIV16("+arreglo[1]+","+arreglo[2]+",sixteenRegister)\n";
            case ">":return mayor_que(arreglo[1],arreglo[2]);
            case "<":return menor_que(arreglo[1],arreglo[2]);
            case "==":return igual_que(arreglo[1],arreglo[2]);
            case "!=":return diferente_que(arreglo[1],arreglo[2]);
            case ">=":return mayor_o_igual_que(arreglo[1],arreglo[2]);
            case "<=":return menor_o_igual_que(arreglo[1],arreglo[2]);
            case ">>":return mayor_que(arreglo[1],arreglo[2]);
            case "<<":return menor_que(arreglo[1],arreglo[2]);
            case "++":return "ADD16("+arreglo[3]+",1,"+arreglo[3]+")\n";
            case "--":return "SUB16("+arreglo[3]+",1,"+arreglo[3]+")\n";
            case "&&":return "AND8("+arreglo[1]+","+arreglo[2]+",eightRegister)\n";
            case "||":return "OR8("+arreglo[1]+","+arreglo[2]+",eightRegister)\n";
            case "!":return "XOR8(1,"+arreglo[3]+",eightRegister)\n";
            case "&":return "AND8("+arreglo[1]+","+arreglo[2]+",eightRegister)\n";
            case "|":return "OR8("+arreglo[1]+","+arreglo[2]+",eightRegister)\n";
            case "spinCraneLeft":return spinCraneLeft(arreglo[1]);
            case "spinCraneRight":return spinCraneRight(arreglo[1]);
            case "moveFowardCrane":return moveFowardCrane(arreglo[1]);
            case "moveBackCrane":return moveBackCrane(arreglo[1]);
            case "spinBallLeft":return spinBallLeft(arreglo[1]);
            case "spinBallRight":return spinBallRight(arreglo[1]);
            case "hitToTheLeft":return hitToTheLeft();
            case "hitToTheRight":return hitToTheRight();
            case "waitTime":return waitTime(arreglo[1]);
            default:break;
        }
        String[] arreglo2=arreglo[0].split(" ");
        arreglo2[1]=arreglo2[1].substring(0,arreglo2[1].length()-1);
        switch(arreglo2[0]){
            case "jmp":return "JR("+arreglo2[1]+")\n";
            case "jf":return salto_falso(arreglo[1],arreglo2[1]);
            case "jt":return salto_verdadero(arreglo[1],arreglo2[1]);
            default:break;
        }
        return "";
        }catch(Exception e){
            return "";
        }
    }
    
    private String igual(String a,String b){
        if(a.substring(0,2).equals("TB")){
            return "MOV8_8(eightRegister,"+b+")\n";
        }
        if(a.equals("true")){
            return "MOV8_8(1,"+b+")\n";
        }
        if(a.equals("false")){
            return "MOV8_8(0,"+b+")\n";
        }
        if(a.substring(0,2).equals("TA")){
            return "MOV16_16(sixteenRegister,"+b+")\n";
        }
        String[] arregloPrueba= res.split("\n");
        for(int i=0;i<arregloPrueba.length;i++){
            if(arregloPrueba[i].equals("DATA8 "+a)){
                return "MOV8_8("+a+","+b+")\n";
            }
        }
        return "MOV16_16("+a+","+b+")\n"; 
    }
    
    private String mayor_que(String a,String b){
        if(a.substring(0,2).equals("TA")){
            return "CP_GT16("+a+",sixteenRegister,eightRegister)\n";
        }
        if(b.substring(0,2).equals("TA")){
            return "CP_GT16(sixteenRegister,"+b+",eightRegister)\n";
        }
        return "CP_GT16("+a+","+b+",eightRegister)\n";
    }
    
    private String menor_que(String a,String b){
        if(a.substring(0,2).equals("TA")){
            return "CP_LT16("+a+",sixteenRegister,eightRegister)\n";
        }
        if(b.substring(0,2).equals("TA")){
            return "CP_LT16(sixteenRegister,"+b+",eightRegister)\n";
        }
        return "CP_LT16("+a+","+b+",eightRegister)\n";
    }
    
    private String igual_que(String a,String b){
        if(a.equals("true") & b.substring(0,2).equals("TB")){
            return "CP_EQ8(1,eightRegister,eightRegister)\n";
        }
        if(a.equals("false") & b.substring(0,2).equals("TB")){
            return "CP_EQ8(0,eightRegister,eightRegister)\n";
        }
        if(b.equals("true") & a.substring(0,2).equals("TB")){
            return "CP_EQ8(eightRegister,1,eightRegister)\n";
        }
        if(b.equals("false") & a.substring(0,2).equals("TB")){
            return "CP_EQ8(eightRegister,0,eightRegister)\n";
        }
        if(a.substring(0,2).equals("TA")){
            return "CP_EQ16("+a+",sixteenRegister,eightRegister)\n";
        }
        if(b.substring(0,2).equals("TA")){
            return "CP_EQ16(sixteenRegister,"+b+",eightRegister)\n";
        }
        String[] arregloPrueba= res.split("\n");
        for(int i=0;i<arregloPrueba.length;i++){
            if(arregloPrueba[i].equals("DATA8 "+a)){
                return "CP_EQ8("+a+","+b+",eightRegister)\n";
            }
        } 
        return "CP_EQ16("+a+","+b+",eightRegister)\n";
    }
    
    private String diferente_que(String a,String b){
        if(a.equals("true") & b.substring(0,2).equals("TB")){
            return "CP_NEQ8(1,eightRegister,eightRegister)\n";
        }
        if(a.equals("false") & b.substring(0,2).equals("TB")){
            return "CP_NEQ8(0,eightRegister,eightRegister)\n";
        }
        if(b.equals("true") & a.substring(0,2).equals("TB")){
            return "CP_NEQ8(eightRegister,1,eightRegister)\n";
        }
        if(b.equals("false") & a.substring(0,2).equals("TB")){
            return "CP_NEQ8(eightRegister,0,eightRegister)\n";
        }
        if(a.substring(0,2).equals("TA")){
            return "CP_NEQ16("+a+",sixteenRegister,eightRegister)\n";
        }
        if(b.substring(0,2).equals("TA")){
            return "CP_NEQ16(sixteenRegister,"+b+",eightRegister)\n";
        }
        String[] arregloPrueba= res.split("\n");
        for(int i=0;i<arregloPrueba.length;i++){
            if(arregloPrueba[i].equals("DATA8 "+a)){
                return "CP_NEQ8("+a+","+b+",eightRegister)\n";
            }
        } 
        return "CP_NEQ16("+a+","+b+",eightRegister)\n";
    }
    
    private String mayor_o_igual_que(String a,String b){
        if(a.substring(0,2).equals("TA")){
            return "CP_GTE16("+a+",sixteenRegister,eightRegister)\n";
        }
        if(b.substring(0,2).equals("TA")){
            return "CP_GTE16(sixteenRegister,"+b+",eightRegister)\n";
        }
        return "CP_GTE16("+a+","+b+",eightRegister)\n";
    }
    
    private String menor_o_igual_que(String a,String b){
        if(a.substring(0,2).equals("TA")){
            return "CP_LTE16("+a+",sixteenRegister,eightRegister)\n";
        }
        if(b.substring(0,2).equals("TA")){
            return "CP_LTE16(sixteenRegister,"+b+",eightRegister)\n";
        }
        return "CP_LTE16("+a+","+b+",eightRegister)\n";
    }
    
    private String spinCraneLeft(String a){
        String r;
        if(a.substring(0,2).equals("TA")){
            r="MOVE16_32(sixteenRegister,timeRegister)\n"
                    +"MUL32(timeRegister,1000)\n"
                    +"OUTPUT_POWER(0,2,-75)\n"
                    +"OUTPUT_POWER(0,4,75)\n"
                    +"OUTPUT_START(0,6)\n"
                    +"TIMER_WAIT(timeRegister,timeCounter)\n"
                    +"TIMER_READY(timeCounter)\n"
                    +"OUTPUT_STOP(0,6,1)\n";
        }else{
            r="MOVE16_32("+a+",timeRegister)\n"
                    +"MUL32(timeRegister,1000)\n"
                    +"OUTPUT_POWER(0,2,-75)\n"
                    +"OUTPUT_POWER(0,4,75)\n"
                    +"OUTPUT_START(0,6)\n"
                    +"TIMER_WAIT(timeRegister,timeCounter)\n"
                    +"TIMER_READY(timeCounter)\n"
                    +"OUTPUT_STOP(0,6,1)\n";
        }
        return r;
    }
    
    private String spinCraneRight(String a){
        String r;
        if(a.substring(0,2).equals("TA")){
            r="MOVE16_32(sixteenRegister,timeRegister)\n"
                    +"MUL32(timeRegister,1000)\n"
                    +"OUTPUT_POWER(0,2,75)\n"
                    +"OUTPUT_POWER(0,4,-75)\n"
                    +"OUTPUT_START(0,6)\n"
                    +"TIMER_WAIT(timeRegister,timeCounter)\n"
                    +"TIMER_READY(timeCounter)\n"
                    +"OUTPUT_STOP(0,6,1)\n";
        }else{
            r="MOVE16_32("+a+",timeRegister)\n"
                    +"MUL32(timeRegister,1000)\n"
                    +"OUTPUT_POWER(0,2,75)\n"
                    +"OUTPUT_POWER(0,4,-75)\n"
                    +"OUTPUT_START(0,6)\n"
                    +"TIMER_WAIT(timeRegister,timeCounter)\n"
                    +"TIMER_READY(timeCounter)\n"
                    +"OUTPUT_STOP(0,6,1)\n";
        }
        return r;
    }
    
    private String moveFowardCrane(String a){
        String r;
        if(a.substring(0,2).equals("TA")){
            r="MOVE16_32(sixteenRegister,timeRegister)\n"
                    +"MUL32(timeRegister,1000)\n"
                    +"OUTPUT_POWER(0,6,75)\n"
                    +"OUTPUT_START(0,6)\n"
                    +"TIMER_WAIT(timeRegister,timeCounter)\n"
                    +"TIMER_READY(timeCounter)\n"
                    +"OUTPUT_STOP(0,6,1)\n";
        }else{
            r="MOVE16_32("+a+",timeRegister)\n"
                    +"MUL32(timeRegister,1000)\n"
                    +"OUTPUT_POWER(0,6,75)\n"
                    +"OUTPUT_START(0,6)\n"
                    +"TIMER_WAIT(timeRegister,timeCounter)\n"
                    +"TIMER_READY(timeCounter)\n"
                    +"OUTPUT_STOP(0,6,1)\n";
        }
        return r;
    }
    
    private String moveBackCrane(String a){
        String r;
        if(a.substring(0,2).equals("TA")){
            r="MOVE16_32(sixteenRegister,timeRegister)\n"
                    +"MUL32(timeRegister,1000)\n"
                    +"OUTPUT_POWER(0,6,-75)\n"
                    +"OUTPUT_START(0,6)\n"
                    +"TIMER_WAIT(timeRegister,timeCounter)\n"
                    +"TIMER_READY(timeCounter)\n"
                    +"OUTPUT_STOP(0,6,1)\n";
        }else{
            r="MOVE16_32("+a+",timeRegister)\n"
                    +"MUL32(timeRegister,1000)\n"
                    +"OUTPUT_POWER(0,6,-75)\n"
                    +"OUTPUT_START(0,6)\n"
                    +"TIMER_WAIT(timeRegister,timeCounter)\n"
                    +"TIMER_READY(timeCounter)\n"
                    +"OUTPUT_STOP(0,6,1)\n";
        }
        return r;
    }
    
    private String spinBallLeft(String a){
        String r;
        if(a.substring(0,2).equals("TA")){
            r="MOVE16_32(sixteenRegister,thirtytwoRegister)\n"
                    +"OUTPUT_STEP_POWER(0,1,75,thirtytwoRegister,0,0,1)\n"
                    +"OUTPUT_START(0,1)\n";
        }else{
            r="MOVE16_32("+a+",thirtytwoRegister)\n"
                    +"OUTPUT_STEP_POWER(0,1,75,thirtytwoRegister,0,0,1)\n"
                    +"OUTPUT_START(0,1)\n";
        }
        return r;
    }
    
    private String spinBallRight(String a){
        String r;
        if(a.substring(0,2).equals("TA")){
            r="MOVE16_32(sixteenRegister,thirtytwoRegister)\n"
                    +"OUTPUT_STEP_POWER(0,1,-75,thirtytwoRegister,0,0,1)\n"
                    +"OUTPUT_START(0,1)\n";
        }else{
            r="MOVE16_32("+a+",thirtytwoRegister)\n"
                    +"OUTPUT_STEP_POWER(0,1,-75,thirtytwoRegister,0,0,1)\n"
                    +"OUTPUT_START(0,1)\n";
        }
        return r;
    }
    
    private String hitToTheLeft(){
        String r="OUTPUT_STEP_POWER(0,1,-75,90,0,0,1)\n" +
                "OUTPUT_START(0,1)\n"+
                "OUTPUT_STEP_POWER(0,1,75,180,0,0,1)\n" +
                "OUTPUT_START(0,1)\n";
        return r;
    }
    
    private String hitToTheRight(){
        String r="OUTPUT_STEP_POWER(0,1,75,90,0,0,1)\n" +
                "OUTPUT_START(0,1)\n"+
                "OUTPUT_STEP_POWER(0,1,-75,180,0,0,1)\n" +
                "OUTPUT_START(0,1)\n";
        return r;
    }
    
    private String salto_falso(String a,String b){
        if(a.substring(0,2).equals("TB")){
            return "JR_FALSE(eightRegister,"+b+")\n";
        }
        if(a.equals("false")){
            return "JR_FALSE(0,"+b+")\n";
        }
        if(a.equals("true")){
            return "JR_FALSE(1,"+b+")\n";
        }
        return "JR_FALSE("+a+","+b+")\n";
    }
    
    private String salto_verdadero(String a,String b){
        if(a.substring(0,2).equals("TB")){
            return "JR_TRUE(eightRegister,"+b+")\n";
        }
        if(a.equals("false")){
            return "JR_TRUE(0,"+b+")\n";
        }
        if(a.equals("true")){
            return "JR_TRUE(1,"+b+")\n";
        }
        return "JR_TRUE("+a+","+b+")\n";
    }
    
    private String waitTime(String a){
        String r;
        if(a.substring(0,2).equals("TA")){
            r="MOV16_32(sixteenRegister,timeRegister)\n"
                    + "MUL32(timeRegister,1000,timeRegister)\n"
                    + "TIMER_WAIT(timeRegister,timeCounter)\n"
                    +"TIMER_READY(timeCounter)\n";
        }else{
            r="MOV16_32("+a+",timeRegister)\n"
                    + "MUL32(timeRegister,1000,timeRegister)\n"
                    + "TIMER_WAIT(timeRegister,timeCounter)\n"
                    +"TIMER_READY(timeCounter)\n";
        }
        return r;
    }    
    
}
