package edu.syr.bytecast.interp.amd64;

import edu.syr.bytecast.amd64.api.constants.*;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import java.util.List;

public interface IISAInstruction {
    
    //Returns true if an instruction matches a certain type.
    public InstructionType getInstructionType();
    
    //Executes the instruction. Instructions should return a non-zero value if
    //they are branches. Instrucutions that branch should not update the
    //instruction pointer.
    public long execute(AMD64Environment env, IInstruction instruction);
}
