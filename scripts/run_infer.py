'''
This script runs infer on all the benchmarks.
Fill in the correct values for the macros at
the top of the file before executing.
'''
import os
import shutil

INFER_COMMAND = "infer "
BENCHMARKS_FOLDER = "../../datasets/june2020_dataset"
RESULTS_FOLDER = "infer_results"
COMPILED_CLASSES_FOLDER = "infer_classes"
SRC_FILES = "infer_srcs.txt"
INFER_REPORT_FILE = "infer-out/report.txt"
INFER_BINARY = "tools/infer/infer-linux64-v1.1.0/bin/infer"
PROGRESS_BAR_OPTION = "--no-progress-bar --biabduction-only"
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
        + " " + INFER_BINARY
        + " " + PROGRESS_BAR_OPTION
        + " " + "-- javac -d"
        + " " + COMPILED_CLASSES_FOLDER
        + " " + " -cp " + lib_folder
        + " @" + SRC_FILES
    )
    os.system(command)

    #Copy the result
    copy_command = ("cp " 
        + INFER_REPORT_FILE 
        + " " + RESULTS_FOLDER 
        + "/" + benchmark + ".txt"
    )
    os.system(copy_command)

    #remove the classes folder
    shutil.rmtree(COMPILED_CLASSES_FOLDER)
