'''
This script runs petablox on all the benchmarks.
Fill in the correct values for the macros at
the top of the file before executing.
'''
import os

PETABLOX_JAR = "tools/petablox/petablox.jar"
BENCHMARKS_FOLDER = "../final_dataset"
RESULTS_FOLDER = "petablox_results"
JAVAC_COMMAND = "javac"
JAVA_COMMAND = "java"
PETABLOX_OPTIONS = "-Dpetablox.datalog.engine=bddbddb -Dpetablox.run.analyses=cipa-0cfa-dlog"
PETABLOX_MAIN_CLASS = "petablox.project.Boot"
PETABLOX_TEMP_RESULT = "petablox_output/methods.txt"
FILE_WITH_MAIN_CLASS = "info/mainclassname"
TIMEOUT = 1800
TIMEOUT_CMD = "timeout"
SKIP_COMPLETED = True #skips if the output file is already there.

#create the output folder if it doesn't exist
results_folder_path = os.path.join(os.getcwd(),RESULTS_FOLDER) 
if not os.path.exists(results_folder_path):
    os.mkdir(results_folder_path)

#Loop through the benchmarks
print("Completed Benchmarks")
for benchmark in os.listdir(BENCHMARKS_FOLDER):
    print(benchmark)
    if (SKIP_COMPLETED):
        if os.path.exists(os.path.join(results_folder_path,(benchmark+".txt"))):
            print("skipping completed benchmark.")
            continue
    benchmark_path = os.path.join(BENCHMARKS_FOLDER,benchmark)
    #skip non-directories
    if not os.path.isdir(benchmark_path):
        continue
    #Get jar file
    jarfile = ''
    for file in os.listdir(os.path.join(benchmark_path,"jarfile")):
        if file.endswith(".jar"):
            jarfile = file
    jarfile_path = os.path.join(benchmark_path,("jarfile/" + jarfile))
    #get main class name
    mainclassname_file = os.path.join(benchmark_path,FILE_WITH_MAIN_CLASS)
    with open(mainclassname_file) as fp:
        mainclass_name = fp.read().splitlines()[0]


    #execute petablox on the jar
    command = (
        TIMEOUT_CMD 
        + " " + str(TIMEOUT)
        + " " + JAVA_COMMAND 
        + " -cp " + jarfile_path
        + ":" + PETABLOX_JAR
        + " " + PETABLOX_OPTIONS
        + " -Dpetablox.class.path=" + jarfile_path
        + " -Dpetablox.main.class=" + mainclass_name
        + " " + PETABLOX_MAIN_CLASS
        )
    os.system(command)

    #copy output to correct place
    cp_command = ("cp" 
        + " " + PETABLOX_TEMP_RESULT
        + " " + results_folder_path
        + "/" + benchmark + ".txt"
        )
    os.system(cp_command)

'''
Command
java -cp big/8cys3qkisi69j2vx9p8dsd373bs1c3cf-url573972e02c_No
taLabs_Praxis_tgz-pJ8-praxis_PraxisControllerJ8/jarfile/url573972e02c_NotaLabs_Praxis_tgz
-pJ8-praxis_PraxisControllerJ8.jar:tools/tmp-petablox/petablox.jar 
-Dpetablox.datalog.engine=bddbddb -Dpetablox.main.class=praxis.PraxisController 
-Dpetablox.run.analyses=cipa-0cfa-dlog 
-Dpetablox.class.path=big/8cys3qkisi69j2vx9p8dsd373bs1c3cf-url573972e02c_NotaLabs_Praxis_tgz-pJ8-praxis_PraxisControllerJ8/jarfile/url573972e02c_NotaLabs_Praxis_tgz-pJ8-praxis_PraxisControllerJ8.jar 
petablox.project.Boot
'''
