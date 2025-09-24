package ru.spbau.tinydb.queries;

import org.jetbrains.annotations.NotNull;
import ru.spbau.tinydb.common.DBException;

/**
 * @author adkozlov
 */
public class Attribute {

    public abstract static class DataType {
    }

    public static class IntegerType extends DataType {

        private static final IntegerType INSTANCE = new IntegerType();

        private IntegerType() {
        }

        @NotNull
        public static IntegerType getInstance() {
            return INSTANCE;
        }

        @Override
        @NotNull
        public String toString() {
            return "integer";
        }
    }

    public static class DoubleType extends DataType {

        private static final DoubleType INSTANCE = new DoubleType();

        private DoubleType() {
        }

        @NotNull
        public static DoubleType getInstance() {
            return INSTANCE;
        }

        @Override
        @NotNull
        public String toString() {
            return "double";
        }
    }

    public static class VarcharType extends DataType {

        public static final int MAX_LENGTH = 255;

        private final int length;

        public VarcharType(int length) {
            if (length < 1) {
                throw new DBException("Varchar length cannot be less than 1.");
            } else if (length > 255) {
                throw new DBException("Varchar length cannot be greater than " + MAX_LENGTH + ".");
            }

            this.length = length;
        }

        public VarcharType() {
            this(MAX_LENGTH);
        }

        public int getLength() {
            return length;
        }

        @Override
        @NotNull
        public String toString() {
            return "varchar(" + length + ")";
        }
    }

    @NotNull
    private final String attributeName;
    @NotNull
    private final DataType dataType;

    public Attribute(@NotNull String attributeName, @NotNull DataType dataType) {
        this.attributeName = attributeName;
        this.dataType = dataType;
    }

    @NotNull
    public String getAttributeName() {
        return attributeName;
    }

    @NotNull
    public DataType getDataType() {
        return dataType;
    }

    @Override
    @NotNull
    public String toString() {
        return "Attribute{" +
                "attributeName='" + attributeName + '\'' +
                ", dataType=" + dataType +
                '}';
    }
}
