package classDiagram;

import java.util.List;

public class Relationship {
    private String extendsRel;
    private String[] implementsRel;
    private manageObject eObj;
    private List<manageObject> iObj;
    private List<manageObject> aObj;
    
    public void setExtendsRel(String extendsRel){
        this.extendsRel=extendsRel;
    }
    public void setImplementsRel(String[] implementsRel){
        this.implementsRel=implementsRel;
    }  
    public void setE(manageObject eObj){
        this.eObj=eObj;
    }
    public void setI(List<manageObject> iObj){
        this.iObj=iObj;
    }
    public void setA(List<manageObject> aObj){
        this.aObj=aObj;
    }
    public String getExtendsRel(){
        return extendsRel;
    }    
    public String[] getImplementsRel(){
        return implementsRel;
    }    
    public manageObject getE(){
        return eObj;
    }
    public List<manageObject> getI(){
        return iObj;
    }
    public List<manageObject> getA(){
        return aObj;
    }
}
