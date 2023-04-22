package caittastic.caitsmod.Block;

import net.minecraft.util.StringRepresentable;

public enum NodeType implements StringRepresentable{
  PULL("pull"),
  PUSH("push");

  public static final NodeType[] BY_ID = values();
  private final String name;

  NodeType(String pName){
    this.name = pName;
  }

  public String getSerializedName(){
    return this.name;
  }
}
