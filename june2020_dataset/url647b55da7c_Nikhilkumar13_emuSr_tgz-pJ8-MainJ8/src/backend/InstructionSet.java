/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

public class InstructionSet
{

    public enum ADD
    {

        ADD, ADDU, ADDH
    }

    public enum MUL
    {

        MUL, MULU, MULH
    }

    public enum CALL
    {

        CALL
    }

    public enum BGT
    {

        BGT
    }

    public enum BEQ
    {

        BEQ
    }

    public enum DIV
    {

        DIV, DIVU, DIVH
    }

    public enum MOD
    {

        MOD, MODU, MODH
    }

    public enum AND
    {

        AND, ANDU, ANDH
    }

    public enum LSR
    {

        LSR
    }

    public enum NOT
    {

        NOT, NOTU, NOTH
    }

    public enum LSL
    {

        LSL
    }

    public enum ASR
    {

        ASR
    }

    public enum OR
    {

        OR, ORH, ORU
    }

    public enum CMP
    {

        CMP, CMPU, CMPH
    }

    public enum SUB
    {

        SUB, SUBU, SUBH
    }

    public enum MOV
    {

        MOV, MOVU, MOVH
    }

    public enum B
    {

        B
    }

    public enum LD
    {

        LD
    }

    public enum NOP
    {

        NOP
    }

    public enum RET
    {

        RET
    }

    public enum ST
    {

        ST
    }

    public enum PRINT
    {

        PRINT
    }
}
