'''
This script runs javaparser on all the benchmarks.
Fill in the correct values for the macros at
the top of the file before executing.
'''
import os

JAVAPARSER_JAR = "tools/javaparser/javaparser-core-3.24.7.jar"
DRIVER_PROGRAM = "tools/javaparser/Driver.java"
BENCHMARKS_FOLDER =  "../../datasets/june2020_dataset"
RESULTS_FOLDER = "results/javaparser_results"
JAVAC_COMMAND = "javac"
JAVA_COMMAND = "java"
TIMEOUT = 1800
TIMEOUT_CMD = "timeout"
SKIP_COMPLETED = True #skips if the output file is already there.

#create the output folder if it doesn't exist
results_folder_path = os.path.join(os.getcwd(),RESULTS_FOLDER)
if not os.path.exists(results_folder_path):
    os.mkdir(results_folder_path)


#add wala jars to classpath. Also add current folder
class_path_string = f'{JAVAPARSER_JAR}:.'
os.environ["CLASSPATH"] = class_path_string
os.system("echo $CLASSPATH")  

#compile the wala driver program. Ensure Java 8 is used.
compile_command = JAVAC_COMMAND + " " + DRIVER_PROGRAM
print(compile_command)
os.system(compile_command)

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
  
  src_files_list = os.path.join(benchmark_path,"info/sources")

  #execute wala on the jar
  wala_command = (TIMEOUT_CMD 
    + " " + str(TIMEOUT)
    + " " + JAVA_COMMAND
    + " " + DRIVER_PROGRAM[:-5] #skip the .java part
    + " " + benchmark_path
    + " " + src_files_list
    + " > " +  results_folder_path
    + "/" + benchmark + ".txt"
  )
  os.system(wala_command)
