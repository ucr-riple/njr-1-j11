'''
This script runs checker-framework on all the benchmarks.
Fill in the correct values for the macros at
the top of the file before executing.
'''
import os
import shutil

BENCHMARKS_FOLDER = "../june2020_dataset"
RESULTS_FOLDER = "checkerframework_results"
COMPILED_CLASSES_FOLDER = "cf_classes"
SRC_FILES = "cf_srcs.txt"
JAVAC_WITH_FLAGS = (
    "javac "
    "-J--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED "
    "-J--add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED "
    "-J--add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED "
    "-J--add-exports=jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED "
    "-J--add-exports=jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED "
    "-J--add-exports=jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED "
    "-J--add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED "
    "-J--add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED "
    "-J--add-opens=jdk.compiler/com.sun.tools.javac.comp=ALL-UNNAMED"
)
CF_ROOT = "tools/checker-framework-3.49.0"
CF_COMMAND = "-processor org.checkerframework.checker.resourceleak.ResourceLeakChecker"
CF_DIST_JAR_ARG = f"-processorpath {CF_ROOT}/checker/dist/checker.jar"
CHECKER_QUAL_JAR = f"{CF_ROOT}/checker/dist/checker-qual.jar"
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
        + " " + JAVAC_WITH_FLAGS
        + " " + CF_DIST_JAR_ARG
        + " " + CF_COMMAND
        + " " + "-d"
        + " " + COMPILED_CLASSES_FOLDER
        + " " + " -cp " + lib_folder + ":" + CHECKER_QUAL_JAR
        + " " + "-Adetailedmsgtext"
        + " " + "-Awarns"
        + " " + "-Xmaxwarns 10000"
        + " @" + SRC_FILES
        + " 2> " +  RESULTS_FOLDER
        + "/" + benchmark + ".txt"
    )
    os.system(command)

    #remove the classes folder
    shutil.rmtree(COMPILED_CLASSES_FOLDER)
    #remove the src files list
    os.remove(SRC_FILES)
