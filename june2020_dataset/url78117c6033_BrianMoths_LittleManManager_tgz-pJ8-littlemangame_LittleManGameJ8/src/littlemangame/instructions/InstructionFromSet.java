/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.instructions;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import littlemangame.instructions.interfaceandimplementations.Instruction;
import littlemangame.instructions.interfaceandimplementations.InstructionOperandTypes;
import littlemangame.instructions.interfaceandimplementations.ParselessInstruction;
import littlemangame.littleman.littlemanutilities.littlemandata.LittleManTest;
import littlemangame.littlemancommands.LittleManCommands.LittleManAction;
import littlemangame.littlemancommands.LittleManCommands.LittleManActionSequence;
import littlemangame.littlemancommands.LittleManCommands.LittleManCommands;
import littlemangame.littlemancommands.LittleManCommands.LittleManConditionalAction;
import littlemangame.word.BinaryWordOperation;
import littlemangame.word.UnaryWordOperation;
import littlemangame.word.Word;

import static littlemangame.instructions.SourceOperand.IMMEDIATE;
import static littlemangame.instructions.SourceOperand.MEMORY;
import static littlemangame.instructions.SourceOperand.REGISTER;
import static littlemangame.instructions.SourceOperand.REGISTER_INDIRECT;
import static littlemangame.instructions.interfaceandimplementations.InstructionOperandTypes.BOTH;
import static littlemangame.instructions.interfaceandimplementations.InstructionOperandTypes.DATA_ONLY;
import static littlemangame.instructions.interfaceandimplementations.InstructionOperandTypes.NEITHER;
import static littlemangame.instructions.interfaceandimplementations.InstructionOperandTypes.PAGE_NUMBER_ONLY;
import static littlemangame.word.BinaryWordOperation.SET;

/**
 * This enum contains all the instructions for the little man. See the in game
 * documentation for explanations of each instruction.
 *
 * @author brian
 */
public enum InstructionFromSet {

    //system, miscellaneous
    NO_OPERATION(0, NEITHER, LittleManCommands.nullAction),
    HALT(9, NEITHER, LittleManCommands.haltAction),
    //control
    UNCONDITIONAL_JUMP(10, DATA_ONLY, SET, IMMEDIATE, DestinationOperand.NOTEBOOK_PAGE_SHEET),
    JUMP_IF_ZERO(11, DATA_ONLY, LittleManTest.ZERO, SET, IMMEDIATE, DestinationOperand.NOTEBOOK_PAGE_SHEET),
    JUMP_IF_NOT_ZERO(12, DATA_ONLY, LittleManTest.NOT_ZERO, SET, IMMEDIATE, DestinationOperand.NOTEBOOK_PAGE_SHEET),
    JUMP_IF_GREATER_THAN_ZERO(13, DATA_ONLY, LittleManTest.GREATER_THAN_ZERO, SET, IMMEDIATE, DestinationOperand.NOTEBOOK_PAGE_SHEET),
    JUMP_IF_GREATER_THAN_OR_EQUAL_TO_ZERO(14, DATA_ONLY, LittleManTest.GREATER_OR_EQUAL_TO_ZERO, SET, IMMEDIATE, DestinationOperand.NOTEBOOK_PAGE_SHEET),
    JUMP_IF_LESS_THAN_OR_EQUAL_TO_ZERO(15, DATA_ONLY, LittleManTest.LESS_OR_EQUAL_ZERO, SET, IMMEDIATE, DestinationOperand.NOTEBOOK_PAGE_SHEET),
    JUMP_IF_LESS_THAN_ZERO(16, DATA_ONLY, LittleManTest.LESS_THAN_ZERO, SET, IMMEDIATE, DestinationOperand.NOTEBOOK_PAGE_SHEET),
    //Input/Output
    PRINT_UNSIGNED(20, NEITHER, LittleManCommands.memorizeDataAtContainerAction(littlemangame.littleman.littlemanutilities.littlemandata.LittleManWordContainer.WORKSHEET), LittleManCommands.getPrintUnsignedToOutputPanelAction()),
    INPUT(25, NEITHER, LittleManCommands.getGetDataFromInputPanelAction(), LittleManCommands.doBinaryOperationOnContainerAction(littlemangame.littleman.littlemanutilities.littlemandata.LittleManWordContainer.WORKSHEET, SET)),
    //data movement
    LOAD_IMMEDIATE(30, DATA_ONLY, SET, IMMEDIATE, DestinationOperand.WORKSHEET),
    LOAD_MEMORY(31, PAGE_NUMBER_ONLY, SET, MEMORY, DestinationOperand.WORKSHEET),
    LOAD_INDIRECT(32, NEITHER, SET, REGISTER_INDIRECT, DestinationOperand.WORKSHEET),
    STORE_IMMEDIATE_INDIRECT(35, DATA_ONLY, SET, REGISTER_INDIRECT, DestinationOperand.NOTEBOOK),
    STORE_REGISTER_MEMORY(36, PAGE_NUMBER_ONLY, SET, REGISTER, DestinationOperand.NOTEBOOK),
    STORE_IMMEDIATE_MEMORY(37, BOTH, SET, IMMEDIATE, DestinationOperand.NOTEBOOK),
    //LOGIC
    COMPLEMENT_WORKSHEET(40, NEITHER, UnaryWordOperation.COMPLEMENT, DestinationOperand.WORKSHEET),
    COMPLEMENT_MEMORY(41, PAGE_NUMBER_ONLY, UnaryWordOperation.COMPLEMENT, DestinationOperand.NOTEBOOK),
    COMPLEMENT_INDIRECT(42, NEITHER, UnaryWordOperation.COMPLEMENT, DestinationOperand.WORKSHEET_INDIRECT),
    DIGITWISE_MAX_IMMEDIATE_REGISTER(50, DATA_ONLY, BinaryWordOperation.DIGITWISE_MAX, IMMEDIATE, DestinationOperand.WORKSHEET),
    DIGITWISE_MAX_MEMORY_REGISTER(51, PAGE_NUMBER_ONLY, BinaryWordOperation.DIGITWISE_MAX, MEMORY, DestinationOperand.WORKSHEET),
    DIGITWISE_MAX_IMMEDIATE_MEMORY(52, BOTH, BinaryWordOperation.DIGITWISE_MAX, IMMEDIATE, DestinationOperand.NOTEBOOK),
    DIGITWISE_MAX_REGISTER_MEMORY(53, PAGE_NUMBER_ONLY, BinaryWordOperation.DIGITWISE_MAX, REGISTER, DestinationOperand.NOTEBOOK),
    DIGITWISE_MIN_IMMEDIATE_REGISTER(55, DATA_ONLY, BinaryWordOperation.DIGITWISE_MIN, IMMEDIATE, DestinationOperand.WORKSHEET),
    DIGITWISE_MIN_MEMORY_REGISTER(56, PAGE_NUMBER_ONLY, BinaryWordOperation.DIGITWISE_MIN, MEMORY, DestinationOperand.WORKSHEET),
    DIGITWISE_MIN_IMMEDIATE_MEMORY(57, BOTH, BinaryWordOperation.DIGITWISE_MIN, IMMEDIATE, DestinationOperand.NOTEBOOK),
    DIGITWISE_MIN_REGISTER_MEMORY(58, PAGE_NUMBER_ONLY, BinaryWordOperation.DIGITWISE_MIN, REGISTER, DestinationOperand.NOTEBOOK),
    //digit twiddling
    LEFT_SHIFT_REGISTER(60, NEITHER, UnaryWordOperation.LEFT_SHIFT, DestinationOperand.WORKSHEET),
    LEFT_SHIFT_MEMORY(61, PAGE_NUMBER_ONLY, UnaryWordOperation.LEFT_SHIFT, DestinationOperand.NOTEBOOK),
    LEFT_SHIFT_INDIRECT(62, NEITHER, UnaryWordOperation.LEFT_SHIFT, DestinationOperand.WORKSHEET_INDIRECT),
    RIGHT_SHIFT_UNSIGNED_REGISTER(63, NEITHER, UnaryWordOperation.RIGHT_SHIFT_UNSIGNED, DestinationOperand.WORKSHEET),
    RIGHT_SHIFT_UNSIGNED_MEMORY(64, PAGE_NUMBER_ONLY, UnaryWordOperation.RIGHT_SHIFT_UNSIGNED, DestinationOperand.NOTEBOOK),
    RIGHT_SHIFT_UNSIGNED_INDIRECT(65, NEITHER, UnaryWordOperation.RIGHT_SHIFT_UNSIGNED, DestinationOperand.WORKSHEET_INDIRECT),
    RIGHT_SHIFT_SIGNED_REGISTER(66, NEITHER, UnaryWordOperation.RIGHT_SHIFT_SIGNED, DestinationOperand.WORKSHEET),
    RIGHT_SHIFT_SIGNED_MEMORY(67, PAGE_NUMBER_ONLY, UnaryWordOperation.RIGHT_SHIFT_SIGNED, DestinationOperand.NOTEBOOK),
    RIGHT_SHIFT_SIGNED_INDIRECT(68, NEITHER, UnaryWordOperation.RIGHT_SHIFT_SIGNED, DestinationOperand.WORKSHEET_INDIRECT),
    //arithmetic
    INCREMENT_REGISTER(70, NEITHER, UnaryWordOperation.INCREMENT, DestinationOperand.WORKSHEET),
    INCREMENT_MEMORY(71, PAGE_NUMBER_ONLY, UnaryWordOperation.INCREMENT, DestinationOperand.NOTEBOOK),
    INCREMENT_INDIRECT(72, NEITHER, UnaryWordOperation.INCREMENT, DestinationOperand.WORKSHEET_INDIRECT),
    DECREMENT_REGISTER(73, NEITHER, UnaryWordOperation.DECREMENT, DestinationOperand.WORKSHEET),
    DECREMENT_MEMORY(74, PAGE_NUMBER_ONLY, UnaryWordOperation.DECREMENT, DestinationOperand.NOTEBOOK),
    DECREMENT_INDIRECT(75, NEITHER, UnaryWordOperation.DECREMENT, DestinationOperand.WORKSHEET_INDIRECT),
    NEGATE_REGISTER(76, NEITHER, UnaryWordOperation.NEGATE, DestinationOperand.WORKSHEET),
    NEGATE_MEMORY(77, PAGE_NUMBER_ONLY, UnaryWordOperation.NEGATE, DestinationOperand.NOTEBOOK),
    NEGATE_INDIRECT(78, NEITHER, UnaryWordOperation.NEGATE, DestinationOperand.WORKSHEET_INDIRECT),
    ADD_IMMEDIATE_TO_REGISTER(80, DATA_ONLY, BinaryWordOperation.ADD, IMMEDIATE, DestinationOperand.WORKSHEET),
    ADD_MEMORY_TO_REGISTER(81, PAGE_NUMBER_ONLY, BinaryWordOperation.ADD, MEMORY, DestinationOperand.WORKSHEET),
    ADD_IMMEDIATE_TO_MEMORY(82, BOTH, BinaryWordOperation.ADD, IMMEDIATE, DestinationOperand.NOTEBOOK),
    ADD_REGISTER_TO_MEMORY(83, PAGE_NUMBER_ONLY, BinaryWordOperation.ADD, REGISTER, DestinationOperand.NOTEBOOK),
    SUBTRACT_IMMEDIATE_FROM_REGISTER(85, DATA_ONLY, BinaryWordOperation.SUBTRACT, IMMEDIATE, DestinationOperand.WORKSHEET),
    SUBTRACT_MEMORY_FROM_REGISTER(86, PAGE_NUMBER_ONLY, BinaryWordOperation.SUBTRACT, MEMORY, DestinationOperand.WORKSHEET),
    SUBTRACT_IMMEDIATE_FROM_MEMORY(87, BOTH, BinaryWordOperation.SUBTRACT, IMMEDIATE, DestinationOperand.NOTEBOOK),
    SUBTRACT_REGISTER_FROM_MEMORY(88, PAGE_NUMBER_ONLY, BinaryWordOperation.SUBTRACT, REGISTER, DestinationOperand.NOTEBOOK);
    static private final Map<Word, InstructionFromSet> instructionMap = new HashMap<>();
    private static EnumMap<InstructionFromSet, String> instructionDescriptions;
    private static EnumMap<InstructionFromSet, String> instructionDetails;

    static {
        for (InstructionFromSet instructionFromSet : InstructionFromSet.values()) {
            instructionMap.put(instructionFromSet.getOpcode(), instructionFromSet);
        }
        makeInstructionDescriptions();
    }

    static void makeInstructionDescriptions() {
        instructionDescriptions = new EnumMap<>(InstructionFromSet.class);
        instructionDetails = new EnumMap<>(InstructionFromSet.class);
        putDescriptionStrings(NO_OPERATION, "The little man does nothing to complete this instruction. He immediately begins the next instruction cycle.");
        putDescriptionStrings(HALT, "The little man stops working on the task completely. No new instructions will be done.");
        putDescriptionStrings(UNCONDITIONAL_JUMP, "The little man sets his page number equal to the data operand.");
        putDescriptionStrings(JUMP_IF_NOT_ZERO, "If the worksheet does not does not have 00 on it, the little man sets his page number equal to the data operand.");
        putDescriptionStrings(JUMP_IF_GREATER_THAN_ZERO, "If the worksheet has a word between 01 and 49 inclusive on it, the little man sets his page number equal to the data operand.");
        putDescriptionStrings(JUMP_IF_GREATER_THAN_OR_EQUAL_TO_ZERO, "If the worksheet has a word between 00 and 49 inclusive on it, the little man sets his page number equal to the data operand.");
        putDescriptionStrings(JUMP_IF_LESS_THAN_OR_EQUAL_TO_ZERO, "If the worksheet has a word between 50 and 99 inclusive or 00 on it, the little man sets his page number equal to the data operand.");
        putDescriptionStrings(JUMP_IF_LESS_THAN_ZERO, "If the worksheet has a word between 50 and 99 inclusive on it, the little man sets his page number equal to the data operand.");
        putDescriptionStrings(PRINT_UNSIGNED, "The little man enters the word written on the worksheet into the output. The word written on the worksheet is unchanged.");
        putDescriptionStrings(INPUT, "The little man goes to the input device and waits for input. Once input is given he copies the word onto the worksheet. Any word previously written on the worksheet is lost.");
        putDescriptionStrings(LOAD_IMMEDIATE, "The little man copies the data operand onto the worksheet. Any word previously written on the worksheet is lost.");
        putDescriptionStrings(LOAD_MEMORY, "The little man reads the word written in the notebook on the page specified by the page number operand. He then copies this word onto the worksheet. Any word previously written on the worksheet is lost.");
        putDescriptionStrings(LOAD_INDIRECT, "The little man reads the word written in on the worksheet. The little man then read the word at the notebook on the page specified by this word. He then copies this word from the notebook onto the worksheet. Any word previously written on the worksheet is lost.");
        putDescriptionStrings(STORE_IMMEDIATE_INDIRECT, "The little man reads the word on the worksheet and writes the data operand into the notebook on the page given by the the word from the worksheet.");
        putDescriptionStrings(STORE_REGISTER_MEMORY, "The little man reads the word on the worksheet and copies this word into the notebook on the page given by the page number operand.");
        putDescriptionStrings(STORE_IMMEDIATE_MEMORY, "The little man writes the data operand in the notebook on the page given by the page number operand.");
        putDescriptionStrings(COMPLEMENT_WORKSHEET, "The little man replaces each digit on the worksheet with its complement. 0 becomes 9, 3 becomes 6, 5 becomes 4, etc.");
        putDescriptionStrings(COMPLEMENT_MEMORY, "The little man replaces with its complement each digit on the notebook page given by the page number operand. 0 becomes 9, 3 becomes 6, 5 becomes 4, etc.");
        putDescriptionStrings(COMPLEMENT_INDIRECT, "The little man replaces with its complement each digit on the notebook page written on the worksheet. 0 becomes 9, 3 becomes 6, 5 becomes 4, etc.");
        putDescriptionStrings(DIGITWISE_MAX_IMMEDIATE_REGISTER, "The little man replaces each digit on the worksheet with the maximum of that digit with the corresponding digit of the data operand.");
        putDescriptionStrings(DIGITWISE_MAX_MEMORY_REGISTER, "The little man replaces each digit on the worksheet with the maximum of that digit with the corresponding digit on the page given by the page number operand.");
        putDescriptionStrings(DIGITWISE_MAX_IMMEDIATE_MEMORY, "The little man replaces each digit on the notebook page given by the page number operand with the maximum of that digit with the corresponding digit of the data operand.");
        putDescriptionStrings(DIGITWISE_MAX_REGISTER_MEMORY, "The little man replaces each digit on the notebook page given by the page number operand with the maximum of that digit with the corresponding digit of the number on the worksheet.");
        putDescriptionStrings(DIGITWISE_MIN_IMMEDIATE_REGISTER, "The little man replaces each digit on the worksheet with the minimum of that digit with the corresponding digit of the data operand.");
        putDescriptionStrings(DIGITWISE_MIN_MEMORY_REGISTER, "The little man replaces each digit on the worksheet with the minimum of that digit with the corresponding digit on the page given by the page number operand.");
        putDescriptionStrings(DIGITWISE_MIN_IMMEDIATE_MEMORY, "The little man replaces each digit on the notebook page given by the page number operand with the minimum of that digit with the corresponding digit of the data operand.");
        putDescriptionStrings(DIGITWISE_MIN_REGISTER_MEMORY, "The little man replaces each digit on the notebook page given by the page number operand with the minimum of that digit with the corresponding digit of the number on the worksheet.");
        putDescriptionStrings(LEFT_SHIFT_REGISTER, "The little man moves each digit on the worksheet to the left. The rightmost digit becomes zero. For example 12 becomes 20.");
        putDescriptionStrings(LEFT_SHIFT_MEMORY, "The little man moves each digit on the notebook page given by the notebook page operand to the left. The rightmost digit becomes zero. For example 12 becomes 20.");
        putDescriptionStrings(LEFT_SHIFT_INDIRECT, "The little man moves each digit on the notebook page given by the register to the left. The rightmost digit becomes zero. For example 12 becomes 20.");
        putDescriptionStrings(RIGHT_SHIFT_UNSIGNED_REGISTER, "The little man moves each digit on the worksheet to the right. The leftmost digit becomes zero. For example 12 becomes 01.");
        putDescriptionStrings(RIGHT_SHIFT_UNSIGNED_MEMORY, "The little man moves each digit on the notebook page given by the notebook page operand to the right. The leftmost digit becomes zero. For example 12 becomes 01.");
        putDescriptionStrings(RIGHT_SHIFT_UNSIGNED_INDIRECT, "The little man moves each digit on the notebook page given by the register to the right. The leftmost digit becomes zero. For example 12 becomes 01.");
        putDescriptionStrings(RIGHT_SHIFT_SIGNED_REGISTER, "The little man moves each digit on the worksheet to the right. The leftmost digit becomes 0 or 9 depending on if the leftmost digit was original four or less. For example 12 becomes 01, but 53 becomes 95.");
        putDescriptionStrings(RIGHT_SHIFT_SIGNED_MEMORY, "The little man moves each digit on the notebook page given by the notebook page operand to the right. The leftmost digit becomes 0 or 9 depending on if the leftmost digit was original four or less. For example 12 becomes 01, but 53 becomes 95.");
        putDescriptionStrings(RIGHT_SHIFT_SIGNED_INDIRECT, "The little man moves each digit on the notebook page given by the register to the right. The leftmost digit becomes 0 or 9 depending on if the leftmost digit was original four or less. For example 12 becomes 01, but 53 becomes 95.");
        putDescriptionStrings(INCREMENT_REGISTER, "The little man increases the number written on the worksheet by one. If the number was originally the maximum number, it goes back to zero.");
        putDescriptionStrings(INCREMENT_MEMORY, "The little man increases by one the number written on the notebook page given by the notebook page operand. If the number was originally the maximum number, it goes back to zero.");
        putDescriptionStrings(INCREMENT_INDIRECT, "The little man increases by one the number written on the notebook page given by the worksheet. If the number was originally the maximum number, it goes back to zero.");
        putDescriptionStrings(DECREMENT_REGISTER, "The little man decreases the number written on the worksheet by one. If the number was originally zero, it becomes the maximum number.");
        putDescriptionStrings(DECREMENT_MEMORY, "The little man decreases by one the number written on the notebook page given by the notebook page operand. If the number was originally zero, it becomes the maximum number.");
        putDescriptionStrings(DECREMENT_INDIRECT, "The little man decreases by one the number written on the notebook page given by the worksheet. If the number was originally zero, it becomes the maximum number.");
        putDescriptionStrings(NEGATE_REGISTER, "The little man replaces the number on the worksheet with its opposite. This has the same effect as taking the digitwise complement of the number and adding one.");
        putDescriptionStrings(NEGATE_MEMORY, "The little man replaces with its opposite the number written on the notebook page given by the notebook page operand. This has the same effect as taking the digitwise complement of the number and adding one.");
        putDescriptionStrings(NEGATE_INDIRECT, "The little man replaces with its opposite  the number written on the notebook page given by the worksheet. This has the same effect as taking the digitwise complement of the number and adding one.");
        putDescriptionStrings(ADD_IMMEDIATE_TO_REGISTER, "The little man adds the data operand to the number written on the worksheet. Only the last two digits are kept.");//get rid of magic number "two"
        putDescriptionStrings(ADD_MEMORY_TO_REGISTER, "The little man adds the number on the notebook page given by the notebook page operand to the number written on the worksheet. Only the last two digits are kept.");//get rid of magic number "two"
        putDescriptionStrings(ADD_IMMEDIATE_TO_MEMORY, "The little man adds the data operand to the number on the notebook page given by the notebook page operand. Only the last two digits are kept.");//get rid of magic number "two"
        putDescriptionStrings(ADD_REGISTER_TO_MEMORY, "The little man adds the number written on the worksheet to the number on the notebook page given by the notebook page operand. Only the last two digits are kept.");//get rid of magic number "two"
        putDescriptionStrings(SUBTRACT_IMMEDIATE_FROM_REGISTER, "The little man subtracts the data operand from the number written on the worksheet. Only the last two digits are kept.");//get rid of magic number "two"
        putDescriptionStrings(SUBTRACT_MEMORY_FROM_REGISTER, "The little man subtracts the number on the notebook page given by the notebook page operand from the number written on the worksheet. Only the last two digits are kept.");//get rid of magic number "two"
        putDescriptionStrings(SUBTRACT_IMMEDIATE_FROM_MEMORY, "The little man subtracts the data operand from the number on the notebook page given by the notebook page operand. Only the last two digits are kept.");//get rid of magic number "two"
        putDescriptionStrings(SUBTRACT_REGISTER_FROM_MEMORY, "The little man subtracts the number written on the worksheet from the number on the notebook page given by the notebook page operand. Only the last two digits are kept.");//get rid of magic number "two"

    }

    private static void putDescriptionStrings(InstructionFromSet instructionFromSet, String description) {
        instructionDescriptions.put(instructionFromSet, makeShortDescriptionString(instructionFromSet));
        instructionDetails.put(instructionFromSet, makeDetailsString(instructionFromSet, description));
    }

    static String makeShortDescriptionString(InstructionFromSet instructionFromSet) {
        StringBuilder descriptionBuilder = new StringBuilder();
        descriptionBuilder
                .append("<html>\n")
                .append(instructionFromSet.getOpcode().toString()).append(": ").append("<i>").append(instructionFromSet.toString()).append("</i>")
                .append("</html>");
        return descriptionBuilder.toString();
    }

    static String makeDetailsString(InstructionFromSet instructionFromSet, String instructionDescription) {
        StringBuilder descriptionBuilder = new StringBuilder();
        descriptionBuilder
                .append("<html>\n")
                .append("<p style=\"width:250px\">").append(instructionDescription).append("</p>").append("\n")
                .append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
                .append("<table border=\"1\" style=\"width:150px\">\n")
                .append("<tr>")
                .append("<td>").append("Takes data operand: ").append("</td>").append("<td>").append(instructionFromSet.instruction.isDataOperandNeeded() ? "yes" : "no").append("</td>").append("\n")
                .append("</tr>")
                .append("<tr>")
                .append("<td>").append("     Takes address operand: ").append("</td>").append("<td>").append(instructionFromSet.instruction.isPageNumberOperandNeeded() ? "yes" : "no").append("</td>").append("\n")
                .append("</tr>")
                .append("</table></html>");
        return descriptionBuilder.toString();
    }

    /**
     * returns the instruction coded for by the given word. An unspecified
     * instruction returned when given a word that does not code for an
     * instruction
     *
     * @param word the word coding for an instruction
     *
     * @return an instruction coded for by the given word, or an unspecified
     * instruction if the given word does not code for an instruction
     */
    static public Instruction decodeInstruction(Word word) {
        final InstructionFromSet instructionFromSet = instructionMap.get(word);
        if (instructionFromSet == null) {
            return NO_OPERATION.getInstruction();
        } else {
            return instructionFromSet.getInstruction();
        }
    }

    private final Instruction instruction;
    private final Word opcode;

    //simple action
    private InstructionFromSet(int opcode, InstructionOperandTypes instructionOperandTypes, LittleManAction... littleManAction) {
        this.opcode = Word.valueOfLastDigitsOfInteger(opcode);
        this.instruction = new ParselessInstruction(instructionOperandTypes, littleManAction);
    }

    //<editor-fold defaultstate="collapsed" desc="operation constructors">
    private InstructionFromSet(int opcode, InstructionOperandTypes instructionOperandTypes, UnaryWordOperation wordOperation, DestinationOperand destinationOperand) {
        this(opcode, instructionOperandTypes, getOperateOnDesitnationAction(wordOperation, destinationOperand));
    }

    private InstructionFromSet(int opcode, InstructionOperandTypes instructionOperandTypes, BinaryWordOperation wordOperation, SourceOperand sourceOperand, DestinationOperand destinationOperand) {
        this(opcode, instructionOperandTypes, getOperateOnDesitnationAction(wordOperation, sourceOperand, destinationOperand));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="test constructors (with operations)">
    private InstructionFromSet(int opcode, InstructionOperandTypes instructionOperandTypes, LittleManTest littleManTest, LittleManAction... littleManAction) {
        this(opcode, instructionOperandTypes, new LittleManConditionalAction(littleManTest, littleManAction));
    }

    private InstructionFromSet(int opcode, InstructionOperandTypes instructionOperandTypes, LittleManTest littleManTest, UnaryWordOperation wordOperation, DestinationOperand destinationOperand) {
        this(opcode, instructionOperandTypes, littleManTest, getOperateOnDesitnationAction(wordOperation, destinationOperand));
    }

    private InstructionFromSet(int opcode, InstructionOperandTypes instructionOperandTypes, LittleManTest littleManTest, BinaryWordOperation wordOperation, SourceOperand sourceOperand, DestinationOperand destinationOperand) {
        this(opcode, instructionOperandTypes, littleManTest, getOperateOnDesitnationAction(wordOperation, sourceOperand, destinationOperand));
    }
    //</editor-fold>

    /**
     * returns the opcode for this instruction
     *
     * @return the opcode for this instruction
     */
    public Word getOpcode() {
        return opcode;
    }

    /**
     * returns a description of this instruction
     *
     * @return a description of this instruction
     */
    public String getDescription() {
        return instructionDescriptions.get(this);
    }

    /**
     * return an html snippet that gives the description and also which operands
     * this instruction takes.
     *
     * @return an html snippet that gives the description and also which
     * operands this instruction takes.
     */
    public String getDetails() {
        return instructionDetails.get(this);
    }

    @Override
    public String toString() {
        final String lowerCaseString = name().replace("_", " ").toLowerCase();
        return lowerCaseString.substring(0, 1).toUpperCase() + lowerCaseString.substring(1);
    }

    /**
     * returns a reset copy of this instruction
     *
     * @return a reset copy of this instruction
     */
    public Instruction getInstruction() {
        return instruction.getResetCopy();
    }

    static private LittleManAction getOperateOnDesitnationAction(UnaryWordOperation unaryWordOperation, DestinationOperand destinationOperand) {
        return new LittleManActionSequence(destinationOperand.getPreparationAction(), LittleManCommands.doUnaryOperationOnContainerAction(destinationOperand.getDestinationContainer(), unaryWordOperation));
    }

    static private LittleManAction getOperateOnDesitnationAction(BinaryWordOperation binaryWordOperation, SourceOperand sourceOperand, DestinationOperand destinationOperand) {
        return new LittleManActionSequence(sourceOperand.getOperandMemorizer(), destinationOperand.getPreparationAction(), LittleManCommands.doBinaryOperationOnContainerAction(destinationOperand.getDestinationContainer(), binaryWordOperation));
    }

}
