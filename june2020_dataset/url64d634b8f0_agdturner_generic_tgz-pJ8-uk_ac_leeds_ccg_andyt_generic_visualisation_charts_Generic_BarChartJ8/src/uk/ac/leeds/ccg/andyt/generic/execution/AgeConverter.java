/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.generic.execution;

import java.util.Random;

/**
 * For converting and classifying age groups.
 */
public class AgeConverter {

    /**
     * @param age
     * @return short of age which is by default 75 and is otherwise:
     * 0,16,20,25,30,45,60,65,70
     */
    public static short getAgeClass1(int age) {
        if (age < 16) {
            return 0;
        }
        if (age < 20) {
            return 16;
        }
        if (age < 25) {
            return 20;
        }
        if (age < 30) {
            return 25;
        }
        if (age < 45) {
            return 30;
        }
        if (age < 60) {
            return 45;
        }
        if (age < 65) {
            return 60;
        }
        if (age < 70) {
            return 65;
        }
        if (age < 75) {
            return 70;
        }
        return 75;
    }

    /**
     * @param age
     * @return short of age which is by default 60 and is otherwise:
     * 0,20,30
     */
    public static short getAgeClass2(int age) {
        if (age < 20) {
            return 0;
        }
        if (age < 30) {
            return 20;
        }
        if (age < 60) {
            return 30;
        }
        return 60;
    }

    /**
     * @param age
     * @return short of age which is by default 75 and is otherwise:
     * 0-15,16,20,25,30,45,60,65,70,75,80,85,90
     */
    public static short getAgeClass3(int age) {
        if (age < 16) {
            return (short) age;
        }
        if (age < 20) {
            return 16;
        }
        if (age < 25) {
            return 20;
        }
        if (age < 30) {
            return 25;
        }
        if (age < 45) {
            return 30;
        }
        if (age < 60) {
            return 45;
        }
        if (age < 65) {
            return 60;
        }
        if (age < 70) {
            return 65;
        }
        if (age < 75) {
            return 70;
        }
        if (age < 80) {
            return 75;
        }
        if (age < 85) {
            return 80;
        }
        if (age < 90) {
            return 85;
        }
        return 90;
    }

    /**
     * @param age
     * @return short of age which is by default 75 and is otherwise:
     * 0,16,20,25,30,45,60,65,70,75,80,85,90
     */
    public static short getAgeClass4(int age) {
        if (age < 16) {
            return 0;
        }
        if (age < 20) {
            return 16;
        }
        if (age < 25) {
            return 20;
        }
        if (age < 30) {
            return 25;
        }
        if (age < 45) {
            return 30;
        }
        if (age < 60) {
            return 45;
        }
        if (age < 65) {
            return 60;
        }
        if (age < 70) {
            return 65;
        }
        if (age < 75) {
            return 70;
        }
        if (age < 80) {
            return 75;
        }
        if (age < 85) {
            return 80;
        }
        if (age < 90) {
            return 85;
        }
        return 90;
    }

    /**
     * @param age
     * @return short of age which is by default 80 and is otherwise:
     * 0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,42,44,46,48,50,
     * 52,54,56,58,60,62,64,66,68,70,72,74,76,78
     */
    public static short getAgeClassHSARDataRecord(int age) {
        if (age < 2) {
            return 0;
        }
        if (age < 4) {
            return 2;
        }
        if (age < 6) {
            return 4;
        }
        if (age < 8) {
            return 6;
        }
        if (age < 10) {
            return 8;
        }
        if (age < 12) {
            return 10;
        }
        if (age < 14) {
            return 12;
        }
        if (age < 16) {
            return 14;
        }
        if (age < 18) {
            return 16;
        }
        if (age < 20) {
            return 18;
        }
        if (age < 22) {
            return 20;
        }
        if (age < 24) {
            return 22;
        }
        if (age < 26) {
            return 24;
        }
        if (age < 28) {
            return 26;
        }
        if (age < 30) {
            return 28;
        }
        if (age < 32) {
            return 30;
        }
        if (age < 34) {
            return 32;
        }
        if (age < 36) {
            return 34;
        }
        if (age < 38) {
            return 36;
        }
        if (age < 40) {
            return 38;
        }
        if (age < 42) {
            return 40;
        }
        if (age < 44) {
            return 42;
        }
        if (age < 46) {
            return 44;
        }
        if (age < 48) {
            return 46;
        }
        if (age < 50) {
            return 48;
        }
        if (age < 52) {
            return 50;
        }
        if (age < 54) {
            return 52;
        }
        if (age < 56) {
            return 54;
        }
        if (age < 58) {
            return 56;
        }
        if (age < 60) {
            return 58;
        }
        if (age < 62) {
            return 60;
        }
        if (age < 64) {
            return 62;
        }
        if (age < 66) {
            return 64;
        }
        if (age < 68) {
            return 66;
        }
        if (age < 70) {
            return 68;
        }
        if (age < 72) {
            return 70;
        }
        if (age < 74) {
            return 72;
        }
        if (age < 76) {
            return 74;
        }
        if (age < 78) {
            return 76;
        }
        if (age < 80) {
            return 78;
        }
        return 80;
    }

    /**
     * @param age
     * @return short of age which is by default 97 and is otherwise:
     * 0-15,16,20,25,30,45,60,65,75,76-96
     */
    public static short getAgeClassISARDataRecord(int age) {
        if (age < 16) {
            return (short) age;
        }
        if (age < 20) {
            return 16;
        }
        if (age < 25) {
            return 20;
        }
        if (age < 30) {
            return 25;
        }
        if (age < 45) {
            return 30;
        }
        if (age < 60) {
            return 45;
        }
        if (age < 65) {
            return 60;
        }
        if (age < 70) {
            return 65;
        }
        if (age < 75) {
            return 70;
        }
        if (age < 96) {
            return (short) age;
        }
        return 97;
    }

    /**
     * @param age
     * @param aRandom
     * @return int age which is by default 90 else a random assignment in ranges:
     * 0-19,20-24,25-29,30-39,40-44,45-49,50-54,55-59,60-64,65-74,75-84,85-90.
     */
    public static int getAge1(
            int age,
            Random aRandom) {
        if (age < 20) {
            return aRandom.nextInt(20);
        }
        if (age < 25) {
            return 20 + aRandom.nextInt(5);
        }
        if (age < 30) {
            return 25 + aRandom.nextInt(5);
        }
        if (age < 35) {
            return 30 + aRandom.nextInt(5);
        }
        if (age < 40) {
            return 35 + aRandom.nextInt(5);
        }
        if (age < 45) {
            return 40 + aRandom.nextInt(5);
        }
        if (age < 50) {
            return 45 + aRandom.nextInt(5);
        }
        if (age < 55) {
            return 50 + aRandom.nextInt(5);
        }
        if (age < 60) {
            return 55 + aRandom.nextInt(5);
        }
        if (age < 65) {
            return 60 + aRandom.nextInt(5);
        }
        if (age < 75) {
            return 65 + aRandom.nextInt(10);
        }
        if (age < 85) {
            return 75 + aRandom.nextInt(10);
        }
        if (age < 90) {
            return 85 + aRandom.nextInt(5);
        }
        return 90;
    }

    /**
     * @return int age which is by default 80 else a random assignment in ranges:
     * 0-19,20-29,30-39,40-49,50-59,60-69,70-79
     * @param age0
     * @param aRandom
     */
    public static int getAge2(
            int age0,
            Random aRandom) {
        if (age0 < 20) {
            return aRandom.nextInt(20);
        }
        if (age0 < 30) {
            return (20 + aRandom.nextInt(10));
        }
        if (age0 < 40) {
            return (30 + aRandom.nextInt(10));
        }
        if (age0 < 50) {
            return (40 + aRandom.nextInt(10));
        }
        if (age0 < 60) {
            return (50 + aRandom.nextInt(10));
        }
        if (age0 < 70) {
            return (60 + aRandom.nextInt(10));
        }
        if (age0 < 80) {
            return (70 + aRandom.nextInt(10));
        }
        return 80;
    }

    /**
     * @param age
     * @param tRandom
     * @return int age which is by default 90 else a random assignment in ranges:
     * 0-4,5-7,8-9,10-14,15,16-19,20-24,25-29,30-44,45-59,60-64,65-74,75-84,85-90.
     */
    public static int getAge3(
            int age,
            Random tRandom) {
        if (age < 5) {
            return tRandom.nextInt(5);
        }
        if (age < 8) {
            return 5 + tRandom.nextInt(3);
        }
        if (age < 10) {
            return 8 + tRandom.nextInt(2);
        }
        if (age < 15) {
            return 10 + tRandom.nextInt(5);
        }
        if (age < 16) {
            return 15;
        }
        if (age < 20) {
            return 16 + tRandom.nextInt(4);
        }
        if (age < 25) {
            return 20 + tRandom.nextInt(5);
        }
        if (age < 30) {
            return 25 + tRandom.nextInt(5);
        }
        if (age < 45) {
            return 30 + tRandom.nextInt(15);
        }
        if (age < 60) {
            return 45 + tRandom.nextInt(15);
        }
        if (age < 65) {
            return 60 + tRandom.nextInt(5);
        }
        if (age < 75) {
            return 65 + tRandom.nextInt(10);
        }
        if (age < 85) {
            return 75 + tRandom.nextInt(10);
        }
        if (age < 90) {
            return 85 + tRandom.nextInt(5);
        }
        return 90;
    }

    /**
     * @param age
     * @param aRandom
     * @return int which is by default age and otherwise a random assignment in ranges:
     * 75-79,80-84,85-90.
     */
    public static int getAge4(
            int age,
            Random aRandom) {
        if (age > 74) {
            if (age < 80) {
                return 75 + aRandom.nextInt(5);
            }
            if (age < 85) {
                return 80 + aRandom.nextInt(5);
            }
            if (age < 90) {
                return 85 + aRandom.nextInt(5);
            }
        }
        return age;
    }

    /**
     * @param age
     * @param aRandom
     * @return int which is a random assignment in ranges:
     * 0-19,20-29,30-59,60-90.
     */
    public static int getAge5(
            int age,
            Random aRandom) {
        if (age < 20) {
            return aRandom.nextInt(20);
        }
        if (age < 30) {
            return 20 + aRandom.nextInt(10);
        }
        if (age < 60) {
            return 30 + aRandom.nextInt(30);
        }
        return 60 + aRandom.nextInt(31);
    }

    /**
     * @param age
     * @param tRandom
     * @return int age which is by default 90 or a random assignement in the
     * ranges:
     * 0-15,16-19,20-24,25-29,30-44,45-59,60-64,65-74,75-79,80-84,85-90.
     */
    public static int getAge7(
            int age,
            Random tRandom) {
        if (age < 16) {
            return tRandom.nextInt(16);
        }
        if (age < 20) {
            return 16 + tRandom.nextInt(4);
        }
        if (age < 25) {
            return 20 + tRandom.nextInt(5);
        }
        if (age < 30) {
            return 25 + tRandom.nextInt(5);
        }
        if (age < 45) {
            return 30 + tRandom.nextInt(15);
        }
        if (age < 60) {
            return 45 + tRandom.nextInt(15);
        }
        if (age < 65) {
            return 60 + tRandom.nextInt(5);
        }
        if (age < 75) {
            return 65 + tRandom.nextInt(5);
        }
        if (age < 80) {
            return 75 + tRandom.nextInt(5);
        }
        if (age < 85) {
            return 80 + tRandom.nextInt(5);
        }
        if (age < 90) {
            return 85 + tRandom.nextInt(5);
        }
        return 90;
    }

    /**
     * @param age0
     * @param tRandom
     * @return int age which is by default 90 else a random assignment in ranges:
     * 0-4,5-7,8-9,10-14,15,16-19,20-24,25-29,30-44,45-59,60-64,65-74,75-84,85-90.
     */
    public static int getAge6(
            int age0,
            Random tRandom) {
        switch (age0) {
            case 0:
                return tRandom.nextInt(16);
            case 16:
                return (16 + tRandom.nextInt(5));
            case 20:
                return (20 + tRandom.nextInt(5));
            case 25:
                return (25 + tRandom.nextInt(5));
            case 30:
                return (30 + tRandom.nextInt(15));
            case 45:
                return (45 + tRandom.nextInt(15));
            case 60:
                return (60 + tRandom.nextInt(5));
            case 65:
                return (65 + tRandom.nextInt(10));
            default:
                return (75 + tRandom.nextInt(21));
        }
    }
}
