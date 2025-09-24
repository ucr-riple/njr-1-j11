package edu.syr.bytecast.interp.amd64.instructions;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.interp.amd64.AMD64Environment;
import edu.syr.bytecast.interp.amd64.IISAInstruction;
import edu.syr.bytecast.interp.amd64.Register;
import java.util.List;

public class ISAInstructionCMP implements IISAInstruction {

    @Override
    public InstructionType getInstructionType() {
        return InstructionType.CMP;
    }

    @Override
    public long execute(AMD64Environment env, IInstruction instruction) {
        List<IOperand> operands = instruction.getOperands();
        if (operands.size() == 2) {
            IOperand op1 = operands.get(0);
            IOperand op2 = operands.get(1);

            int op_width1 = env.getOperandWidth(op1);
            int op_width2 = env.getOperandWidth(op1);

            long val1 = env.getValue(op1, op_width1);
            long val2 = env.getValue(op2, op_width2);

            long diff = val1 - val2;

            //CMP doesnt save the actual difference anywhere... it just
            //updates the EFLAGS register
            Register dummyReg = new Register();
            dummyReg.setValue(diff, op_width1 - 1, 0);
            long returned = dummyReg.getValue(op_width1-1, 0);

            if (checkOverflow(val1, val2, returned)) {
                env.setValue(RegisterType.OF, 1);
            } else {
                env.setValue(RegisterType.OF, 0);
            }

            if (checkCarry(val1, val2, returned)) {
                env.setValue(RegisterType.CF, 1);
            } else {
                env.setValue(RegisterType.CF, 0);
            }

            if (returned == 0) {
                env.setValue(RegisterType.ZF, 1);
            } else {
                env.setValue(RegisterType.ZF, 0);
            }

            if (returned < 0) {
                env.setValue(RegisterType.SF, 1);
            } else {
                env.setValue(RegisterType.SF, 0);
            }

            if (ISAUtil.isEvenParity(returned)) {
                env.setValue(RegisterType.PF, 1);
            } else {
                env.setValue(RegisterType.PF, 0);
            }
        }
        return 0;
    }

    private static boolean checkOverflow(long a, long b, long result) {
        if (a < 0 && b > 0 && result > 0) {
            return true;
        } else if (a > 0 && b < 0 && result < 0) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkCarry(long a, long b, long result) {
        if (a > 0 && b < 0) {
            return true;
        } else if (a < 0 && b < 0 && result < 0) {
            return true;
        } else {
            return false;
        }
    }
}