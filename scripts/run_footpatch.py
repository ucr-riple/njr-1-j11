'''
This script runs infer on all the benchmarks.
Fill in the correct values for the macros at
the top of the file before executing.
'''
import os
import shutil

INFER_COMMAND = "infer "
BENCHMARKS_FOLDER = "../njr/june2020_dataset"
RESULTS_FOLDER = "results"
COMPILED_CLASSES_FOLDER = "infer_classes"
SRC_FILES = "infer_srcs.txt"
FOOTPATCH_FIXES_LOC = "infer-out/footpatch"
INFER_BINARY = "/home/vagrant/footpatch/infer-linux64-v0.9.3/infer/bin/infer"
PROGRESS_BAR_OPTION = "--no-progress-bar"
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
        if os.path.exists(RESULTS_FOLDER + "/" + benchmark):
            print("skipping completed benchmark.")
            continue
    #skip non-directories
    if not os.path.isdir(BENCHMARKS_FOLDER + "/" + benchmark):
        continue
    
    #create a folder for the compiled classes if it doesn't exist
    if not os.path.exists(COMPILED_CLASSES_FOLDER):
        os.mkdir(COMPILED_CLASSES_FOLDER)

    #Get a list of Java source code files.
    find_srcs_command = "find " + BENCHMARKS_FOLDER + "/" + benchmark + '/src -name "*.java" > ' + SRC_FILES
    os.system(find_srcs_command)
    
    #get folder with libraries used by benchmark
    lib_folder = BENCHMARKS_FOLDER + "/" + benchmark + "/lib"

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
    copy_command = ("cp -r " 
        + FOOTPATCH_FIXES_LOC 
        + " " + RESULTS_FOLDER 
        + "/" + benchmark
    )
    os.system(copy_command)

    #remove the classes folder
    shutil.rmtree(COMPILED_CLASSES_FOLDER)
