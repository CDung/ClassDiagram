package classDiagram;

public class manageMember {
    private String name;
    private String fullName;
    
    public manageMember(){
        name=null;
        fullName=null;   
    }
    public manageMember(String fullName){
        this.fullName=fullName;   
    }    
    public manageMember(String name, String fullName){
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
    
