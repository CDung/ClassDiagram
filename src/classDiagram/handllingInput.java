package classDiagram;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class handllingInput {
    private final List<File> listFile=new ArrayList();
    private final List<String> listString=new ArrayList();
    private final List<manageObject> listObject=new ArrayList();
    
    private List<List<manageObject>> listSortObject=new ArrayList();
    
 
    
    private  void add(String fileName) throws FileNotFoundException{  
        String content = new Scanner(new File(fileName)).useDelimiter("\\Z").next();        
        content=deleteString(content,'\'');
        content=deleteString(content,'\"');
        content=deleteComment(content);
        content=standardized(content);
        String[] contents=content.split("([\\w ]*(?= class )|(?= interface ))"); // tách ra lấy nhiều class trong 1 file
        
        String temp=null;
        for(String c:contents){            
            c=deleteBodyOfMethod(c);    // delete thân các method
            if (c!=null){                // tách ra lấy các iner class
                if(!c.contains("}")) temp=c;
                else{                 
                    listString.add(c.substring(0,c.indexOf("}")+1));
                    temp=temp+c.substring(c.indexOf("}")+1,c.length());                
                }
                if(temp!=null)
                    if(temp.contains("}")){
                        listString.add(temp);
                        temp=null;
                }                        
            }
        }
}

    private String deleteString(String string,char x){
        char[] str=string.toCharArray();
        for (int index=0;index<str.length;index++){
            if(str[index]==x){
                int count =0;
                int i=index;
                str[i]=0;
                while(true){
                    i++ ;if (i==str.length) break;
                    if(str[i]==x && count%2==0){
                        str[i]=0;
                        break;
                    }
                    if(str[i]=='\\') count++ ;else count=0;
                    str[i]=0;
                }          
            }
        }
        return new String(str);
    }
    
    private String deleteComment(String string){
        StringBuffer str;
        str = new StringBuffer(string);
        while (str.indexOf("//")!=-1){
            int startErase=str.indexOf("//");
            int endErase=str.indexOf("\n",startErase);
            str.delete(startErase,endErase+1);
        }
        while (str.indexOf("@")!=-1){
            int startErase=str.indexOf("@");
            int endErase=str.indexOf("\n",startErase);
            str.delete(startErase,endErase+1);
        }
        while (str.indexOf("/*")!=-1){
            int startErase=str.indexOf("/*");
            int endErase=str.indexOf("*/");
            str.delete(startErase,endErase+3);
        }
        return str.toString();
    }
    
    private String standardized(String input){
        // đưa lên một dòng
        StringTokenizer stringtokenizer=new StringTokenizer(input);
        input="";
        while(stringtokenizer.hasMoreTokens())
           input=input+stringtokenizer.nextToken()+" ";
        input= input.trim();
        //xóa kí tự null
        StringBuffer stringbuffer=new StringBuffer();
        for(int i=0;i<input.length();i++)
            if(input.charAt(i)!=0)                
                stringbuffer.append(input.charAt(i));
        return stringbuffer.toString();
    }
        
    private String deleteBodyOfMethod(String string){ 
        char[] str=string.toCharArray();
        int i=string.indexOf('{')+1;
        if (i==0) return null;
        while(i<str.length){
            if (str[i]=='{'){
                str[i]=0;
                int count=1;
                i++;
                while(i<str.length){                
                    if (str[i]=='{') ++count;
                    if (str[i]=='}') --count;                                                           
                    if (count==0){
                        str[i]=';';
                        break;
                    }
                    str[i]=0;
                    i++;
                }
            }
            else{ 
                i++;
            }
        }
        for(i=0;i<str.length-2;i++){
            if (str[i]==';')
                if(str[i+1]==',' ||(str[i+1]==' ' && str[i+2]==',')) str[i]=0;
        }
        return new String(str);
    }
    
    private String deleteBrackets(String string,char x,char y){ 
        char[] str=string.toCharArray();
        int i=0;
        while(i<str.length){
            if (str[i]==x){
                int count=1;
                i++;
                while(i<str.length){                
                    if (str[i]==x) ++count;
                    if (str[i]==y) --count;                                                           
                    if (count==0) break;
                    str[i]=0;
                    i++;
                }
            }
            else i++;
        }
        return new String(str);
    }
    
    private String deleteExpression(String string){
        string=deleteBrackets(string,'(',')');
        string=deleteBrackets(string,'[',']');
        char[] str=string.toCharArray();
        for (int i=0;i<string.length();i++){
            if (str[i]=='=')
                while(i<string.length() && str[i]!=','){
                    str[i]=0;                
                    i++;
                }
        }
        return new String(str);        
    }
       
    private String deleteModify(String string){
        String[] listModify={"private","public","protected","final","abstract","static"};
        String expresent="",output="";
        for (String str : listModify) {
            expresent=expresent+"(<?\\b"+str+" )?";
        }
        Matcher findModify =Pattern.compile(expresent).matcher(string);
        while(findModify.find()){
            output=findModify.replaceAll("");
        }
        return output;
    }
    
    private String setTyper(String string){
        String str="";
        string=standardized(string);
        Matcher finds1 =Pattern.compile("\\w+ ?\\(").matcher(string);
        Matcher finds2 =Pattern.compile("\\w+( ?, ?\\w+ ?)*\\z").matcher(string);
        if(finds1.find()){
            str=finds1.group();
            return (string.substring(string.indexOf(str))+" : "+string.substring(0,string.indexOf(str))).trim();
        }
        if(finds2.find()){
            str=finds2.group();
            return (string.substring(string.lastIndexOf(str))+" : "+string.substring(0,string.lastIndexOf(str))).trim();
        }
        return string.toString();
    }
  
    private void setListFile(File file){        
        if (file.isDirectory()){
            File[] children = file.listFiles();
             for (File child : children) 
                 setListFile(child);
            }
        else
            if (file.getName().endsWith(".java"))
                listFile.add(file);        
        }
    
    private void setListString() throws FileNotFoundException{        
        for(File file : listFile){
            add(file.getPath()); 
        }
    }
  
    public  void set(File[] chooserFile) throws FileNotFoundException{
        for(File f: chooserFile) setListFile(f);           
        setListString(); 
        setFullNameObject();
        setNameObject();
        setRelObject();
        processRel();
        sortObj();
    }
    
    private void setFullNameObject(){
        for(String string:listString ) {
            String x=string.substring(0,string.indexOf('{')); // chứa tên class,interface
            String[] y=string.substring(string.indexOf('{')+2,string.length()) .split("( ?; ?)");//phần thân
            
            manageObject newObject=new manageObject();
            if(x.contains(" class ") || x.startsWith("class ")){
                newObject=new manageClass(x);
            }
            else{
                newObject=new manageInterface(x);
            }
            List<manageMember> listMember=new ArrayList();            
            List<manageMethod> listMethod=new ArrayList();
            for(String sentences:y){
                if (sentences.length()<3)
                    continue;
                if (sentences.contains("(") && !sentences.contains("=") ){
                    manageMethod newMethod=new manageMethod(sentences);
                    listMethod.add(newMethod);
                    continue;
                }
                if ( sentences.startsWith("abstract")){
                    manageMethod newMethod=new manageMethod(sentences);
                    listMethod.add(newMethod);
                    continue;
                }
                manageMember newMember=new manageMember(sentences);
                listMember.add(newMember);                    
            }
            newObject.setListMember(listMember);
            newObject.setListMethod(listMethod);
            listObject.add(newObject);                       
        }
    }
        
    private void setNameObject(){
        for(manageObject obj :listObject ){
            String matchName = null;
            if (obj  instanceof manageClass){
                matchName="class (\\w+)";
            }
            else{
                matchName="interface (\\w+)";
            }
            Matcher findNameObj =Pattern.compile(matchName).matcher(obj.getFullName());
            if (findNameObj.find())
                obj.setName(findNameObj.group(1));
                        
            for(manageMethod method : obj.getListMethod()){
                Matcher findNameMethod =Pattern.compile(".+\\(.*\\)").matcher(method.getFullName());
                if (findNameMethod.find())
                    method.setName(findNameMethod.group(0));
                method.setName(deleteModify(method.getName()));
                if(!method.getName().startsWith(obj.getName()))
                    method.setName(setTyper(method.getName()));
                if(method.getFullName().contains("private "))
                    method.setName("- "+method.getName());
                else if (method.getFullName().contains("public "))
                    method.setName("+ "+method.getName());    
                else if (method.getFullName().contains("protected "))
                    method.setName("# "+method.getName());
                else
                    method.setName("~ "+method.getName());
            }
            
            for(manageMember member : obj.getListMember()){
                if(member.getFullName().contains("private "))
                    member.setName("- "+setTyper(deleteExpression(deleteModify(member.getFullName()))));
                else if (member.getFullName().contains("public "))
                    member.setName("+ "+setTyper(deleteExpression(deleteModify(member.getFullName()))));
                else if (member.getFullName().contains("protected "))
                    member.setName("# "+setTyper(deleteExpression(deleteModify(member.getFullName()))));
                else
                    member.setName("~ "+setTyper(deleteExpression(deleteModify(member.getFullName()))));
            }
        }
    }
    
    private void setRelObject(){
        for(manageObject obj :listObject ){
            String str=obj.getFullName();
            Relationship Rel=new Relationship();
            
            Matcher m1=Pattern.compile(" extends (\\w+)").matcher(str);
            if(m1.find()){
                String extendsRel=m1.group(1);
                Rel.setExtendsRel(extendsRel);
                str=m1.replaceAll("");
            }
            
            Matcher m2=Pattern.compile(" implements ([\\w ,]+)").matcher(str);
            if (m2.find()){
                String[] implementsRel=m2.group(1).split("(, ?)");
                Rel.setImplementsRel(implementsRel);
            }            
            obj.setRel(Rel);
        }
    }
    
    private void processRel(){
        for(manageObject obj :listObject){
            String e=obj.getRel().getExtendsRel();
            String[] I=obj.getRel().getImplementsRel();
            List<manageObject> listI=new ArrayList();
            List<manageObject> listA=new ArrayList();
            
            for(manageObject eObj :listObject)
                if(eObj!=obj && eObj.getName().equals(e)){
                    obj.getRel().setE(eObj);
                    break;
                }
            
            if(I!=null)
            for(String i:I)
                for(manageObject iObj :listObject)
                    if(iObj!=obj && iObj.getName().equals(i)){
                        listI.add(iObj);
                        break;
                    }
            obj.getRel().setI(listI);
            
            for(manageMember m:obj.getListMember())
                for(manageObject aObj :listObject)
                    if (m.getName().contains(aObj.getName())) listA.add(aObj);
            obj.getRel().setA(listA);
        }
    }
    
    private void sortObj(){
        List<manageObject> list=listObject;
//        for( manageObject o:listObject){
//            System.out.println("class name : " +o.getName());
//            for (manageMember mem : o.getListMember()){
//                System.out.println("membor: " +mem.getName());
//            }
//            for (manageMethod met : o.getListMethod()){
//                System.out.println("method: " +met.getName());
//            }
//            System.out.println("***********************");
//        }
        List<List<manageObject>> sortList=new ArrayList();
        List<manageObject> firstList=new ArrayList();
        int i=0;
        while(i<list.size()) {
            manageObject obj=list.get(i);
            if(obj.getRel().getE()==null && obj.getRel().getI().isEmpty() && obj.getRel().getA().isEmpty()){
                firstList.add(obj);
                list.remove(obj);
                i=0;
            }
            else i++;
        }
        sortList.add(firstList);
        while(list.size()>0){
            List<manageObject> l=new ArrayList();
            for(manageObject obj: sortList.get(sortList.size()-1)){
                i=0;
                while(i<list.size()){
                   boolean check=false;
                   if(list.get(i).getRel().getE()!=null)
                       if(list.get(i).getRel().getE()==obj) check=true;
                   for(manageObject iObj:list.get(i).getRel().getI()) 
                       if (iObj==obj){
                           check=true;
                           break;
                       }
                   for(manageObject aObj:list.get(i).getRel().getA()) 
                       if (aObj==obj){
                           check=true;
                           break;
                       }
                   if(check){
                       l.add(list.get(i));
                       list.remove(i);
                       i=0;
                   } else i++;
               } 
            }
            sortList.add(l);
        }  
        listSortObject=sortList;
    }
       
    public List<List<manageObject>> get(){
        return  listSortObject;
    }
}

