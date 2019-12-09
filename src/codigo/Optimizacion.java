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
public class Optimizacion {
    private String lmsf;
    int s,cS;
    int l,cL;
    public Optimizacion(String lms){
        lmsf=lms;
        s=l=cS=cL=0;
    }
    public String opt(){
        String res=optimizarFlujo(lmsf);
        res=optimizarVariables(res);
        return res;
    }

    private String optimizarFlujo(String a) {
        String[] cuarC=a.split("\n");
        for(int i=0;i<cuarC.length;i++){
            if(cuarC[i].matches("choice[0-9]+:")){
                cS++;
            }
            if(cuarC[i].matches("loop[0-9]+:")){
                cL++;
            }
        }
        String o1=a;
        do{
            o1=optimizarSeleccion(o1);
            s++;
        }while(s<cS);
        String o2=o1;
        do{
            o2=optimizarLoop(o2);
            l++;
        }while(l<cL);   
        return o2;
    }

    private String optimizarLoop(String a) {
        boolean loopC=true;
        String[] prueba=a.split("\n");
        int i=0;
        do{
            if(prueba[i].equals("startloop"+l+":")){
                int startLoop=i;
                i++;
                int itemp=i;
                do{
                    if(prueba[i].equals("(jf loop"+l+":,true, , )")){
                        prueba[i]="";
                        boolean eliminar=true;
                        i++;
                        do{
                            if(prueba[i].equals("(jmp loop"+l+":, , , )")){
                                eliminar=false;
                            }
                            i++;
                        }while(!prueba[i].equals("loop"+l+":"));
                        if(eliminar){
                            prueba[i]="";
                        }
                        loopC=false;
                        i++;
                        if(eliminar){
                            do{
                                prueba[i]="";
                                i++;
                            }while(i<prueba.length);
                        }
                        l++;
                        break;
                    }
                    if(prueba[i].equals("(jf loop"+l+":,false, , )")){
                        prueba[startLoop]="";
                        boolean b=true;
                        do{
                            if(prueba[i].equals("loop"+l+":")){
                                loopC=false;
                                b=false;
                            }
                            prueba[i]="";
                            i++;
                        }while(b);
                        l++;
                        break;
                    }
                    if(prueba[i].equals("(jt startloop"+l+":,true, , )")){
                        i++;
                        boolean b=true;
                        do{
                            if(prueba[itemp].equals("(jmp loop"+l+":, , , )")){
                                b=false;     
                            }
                            itemp++;
                        }while(!prueba[itemp].equals("loop"+l+":"));
                        loopC=false;
                        if(b){
                            do{
                                prueba[i]="";
                                i++;
                            }while(prueba.length>i);
                        }
                        l++;
                        break;
                    }
                    if(prueba[i].equals("loop"+l+":")){
                        loopC=false;
                        l++;
                    }
                    i++;
                }while(loopC);
            }
            i++;
        }
        while(prueba.length>i);
        String res="";
        for(int i2=0;i2<prueba.length;i2++){
            if(!prueba[i2].equals("")){
                res=res+prueba[i2]+"\n";
            }
        }
        return res;
    }
    
    private String optimizarSeleccion(String a){
        String[] prueba=a.split("\n");
        int i=0;
        do{
            if(prueba[i].equals("(jf choice"+s+":,false, , )")){
                prueba[i]="";
                boolean b=true;
                boolean b2=false;
                do{
                    if(prueba[i].equals("choice"+s+":")){
                        b=false;
                    }
                    if(prueba[i].equals("(jmp choice"+(s+1)+":, , , )")){
                        b2=true;
                    }
                    prueba[i]="";
                    i++;
                }while(b);
                int i2=i;
                if(b2){
                do{
                    if(prueba[i2].equals("choice"+(s+1)+":")){
                        prueba[i2]="";
                        s++;
                        break;
                    }
                    i2++;
                }while(i2<prueba.length);
                }
            }
            if(prueba[i].equals("(jf choice"+s+":,true, , )")){
                prueba[i]="";
                i++;
                boolean r=true;
                boolean s2=false;
                do{
                    if(prueba[i].equals("choice"+s+":")){
                        r=false;
                        prueba[i]="";
                    }
                    if(prueba[i].equals("(jmp choice"+s+", , , )")){
                        s2=true;
                        prueba[i]="";
                    }
                    i++;
                }while(r);
                if(s2){
                    s++;
                    boolean b2=true;
                    do{
                        if(prueba[i].equals("choice"+s+":")){
                            b2=false;
                        }
                        prueba[i]="";
                        i++;
                    }while(b2);
                    i--;
                }  
            }
            i++;
        }while(i<prueba.length);
        String res="";
        for(int id=0;id<prueba.length;id++){
            if(!prueba[id].equals("")){
                res=res+prueba[id]+"\n";
            }
        }
        return res;
    }
    
    private String optimizarVariables(String a){
        String[] cuartetos=a.split("\n");
        ArrayList<String> enteros=new ArrayList<String>();
        ArrayList<String> booleanos=new ArrayList<String>();
        for(int i=0;i<cuartetos.length;i++){
            String[] elementos=cuartetos[i].substring(1,cuartetos[i].length()-1).split(",");
            if(elementos[0].equals("int")){
                enteros.add(elementos[3]);
            }
            if(elementos[0].equals("bool")){
                booleanos.add(elementos[3]);
            }
        }
        for(int i=0;i<enteros.size();i++){
            boolean useful=false;
            for(int j=0;j<cuartetos.length;j++){
                if(!cuartetos[j].matches("[a-zA-Z_0-9]+:") && !cuartetos[j].equals("")){
                    String[] elementos=cuartetos[j].substring(1,cuartetos[j].length()-1).split(",");
                    if(elementos[1].equals(enteros.get(i)) || elementos[2].equals(enteros.get(i))){
                        useful=true;
                    }
                }
            }
            if(!useful){
                for(int j=0;j<cuartetos.length;j++){
                    if(!cuartetos[j].matches("[a-zA-Z_0-9]+:") && !cuartetos[j].equals("")){
                        String[] elementos=cuartetos[j].substring(1,cuartetos[j].length()-1).split(",");
                        if(elementos[3].equals(enteros.get(i))){
                            cuartetos[j]="";
                        }
                    }
                }
            }
        }
        for(int i=0;i<booleanos.size();i++){
            boolean useful=false;
            for(int j=0;j<cuartetos.length;j++){
                if(!cuartetos[j].matches("[a-zA-Z_0-9]+:") && !cuartetos[j].equals("")){
                    String[] elementos=cuartetos[j].substring(1,cuartetos[j].length()-1).split(",");
                    if(elementos[1].equals(booleanos.get(i)) || elementos[2].equals(booleanos.get(i))){
                        useful=true;
                    }
                }
            }
            if(!useful){
                for(int j=0;j<cuartetos.length;j++){
                    if(!cuartetos[j].matches("[a-zA-Z_0-9]+:") && !cuartetos[j].equals("")){
                        String[] elementos=cuartetos[j].substring(1,cuartetos[j].length()-1).split(",");
                        if(elementos[3].equals(booleanos.get(i))){
                            cuartetos[j]="";
                        }
                    }
                }
            }
        }
        String res="";
        for(int i=0;i<cuartetos.length;i++){
            if(!cuartetos[i].equals("")){
                res=res+cuartetos[i]+"\n";
            }
        }
        return res;
    }
}
