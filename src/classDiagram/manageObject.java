package classDiagram;

import java.util.List;

public class manageObject {
    private String name;
    private String fullName;    
    private List<manageMember> listMember;
    private List<manageMethod> listMethod;
    private Relationship Rel;
    
    public manageObject(){
        name=null;
        fullName=null;   
        listMember=null;
        listMethod=null; 
        Rel=null;
    }    
    public manageObject(String fullName){
        name=null;
        this.fullName=fullName;   
        listMember=null;
        listMethod=null; 
        Rel=null;
    }    
    public void setName(String name){
        this.name=name;
    }    
    public void setFullName(String fullName){
        this.fullName=fullName;
    }    
    public void setListMember(List<manageMember> listMember){
        this.listMember=listMember;
    }    
    public void setListMethod(List<manageMethod> listMethod){
        this.listMethod=listMethod;
    }    
    public void setRel(Relationship Rel){
        this.Rel=Rel;
    }    
    public String getName(){
        return name;
    }    
    public String getFullName(){
        return fullName;
    }    
    public List<manageMember> getListMember(){
        return listMember;
    }    
    public List<manageMethod> getListMethod(){
        return listMethod;
    } 
    public Relationship getRel(){
        return Rel;
    }
    public String maxLength(){
        String max = name;
        for (manageMember member :listMember){
            if(member.getName().length() > max.length()) max = member.getName();
        }
        for (manageMethod method :listMethod){
            if(method.getName().length() > max.length()) max = method.getName();
        }
        return max;
    }
}