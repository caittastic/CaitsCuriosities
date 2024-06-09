package caittastic.caitsmod.blocks;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum NodeType implements StringRepresentable{
  PULL("pull"),
  PUSH("push");

  private final String name;

  NodeType(String pName){
    this.name = pName;
  }

  public @NotNull String getSerializedName(){
    return this.name;
  }
}
