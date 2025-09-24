'''
This script runs checker-framework on all the benchmarks.
Fill in the correct values for the macros at
the top of the file before executing.
'''
import os
import shutil

BENCHMARKS_FOLDER = "../final_dataset"
RESULTS_FOLDER = "checkerframework_results"
COMPILED_CLASSES_FOLDER = "cf_classes"
SRC_FILES = "cf_srcs.txt"
CF_BINARY = "tools/checker-framework-3.11.0/checker/bin/javac"
CF_COMMAND = "-processor org.checkerframework.checker.nullness.NullnessChecker"
SKIP_COMPLETED = True #skips if the output file is already there.
TIMEOUT = 1800
TIMEOUT_CMD = "timeout"

#create the output folder if it doesn't exist
if not os.path.exists(RESULTS_FOLDER):
    os.mkdir(RESULTS_FOLDER)

#Loop through the benchmarks
print("Completed Benchmarks")
for benchmark in os.listdir(BENCHMARKS_FOLDER):
    print(benchmark)
    if (SKIP_COMPLETED):
        if os.path.exists(f'{RESULTS_FOLDER}/{benchmark}.txt'):
            print("skipping completed benchmark.")
            continue
    #skip non-directories
    if not os.path.isdir(f'{BENCHMARKS_FOLDER}/{benchmark}'):
        continue
    
    #create a folder for the compiled classes if it doesn't exist
    if not os.path.exists(COMPILED_CLASSES_FOLDER):
        os.mkdir(COMPILED_CLASSES_FOLDER)

    #Get a list of Java source code files.
    find_srcs_command = f'find {BENCHMARKS_FOLDER}/{benchmark}/src -name "*.java" > {SRC_FILES}'
    os.system(find_srcs_command)

    #get folder with libraries used by benchmark
    lib_folder = f'{BENCHMARKS_FOLDER}/{benchmark}/lib'

    #execute infer on the source files
    command = (TIMEOUT_CMD 
        + " " + str(TIMEOUT)
        + " " + CF_BINARY
        + " " + CF_COMMAND
        + " " + "-d"
        + " " + COMPILED_CLASSES_FOLDER
        + " " + " -cp " + lib_folder
        + " @" + SRC_FILES
        + " 2> " +  RESULTS_FOLDER
        + "/" + benchmark + ".txt"
    )
    os.system(command)

    #remove the classes folder
    shutil.rmtree(COMPILED_CLASSES_FOLDER)
