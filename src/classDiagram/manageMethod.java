package classDiagram;

public class manageMethod {
    private String name;
    private String fullName;
    private String returnMethod;
    
    public manageMethod(){
        name=null;
        fullName=null;   
    }
    public manageMethod(String fullName){
        this.fullName=fullName;   
    }
    public manageMethod(String name, String fullName){
        this.name=name;
        this.fullName=fullName;   
    }
    public void setName(String name){
        this.name=name;
    }
    public void setFullName(String fullName){
        this.fullName=fullName;
    }
    public String getName(){
        return name;
    }
    public String getFullName(){
        return fullName;
    } 
    
}
