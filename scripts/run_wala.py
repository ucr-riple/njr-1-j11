'''
This script runs wala on all the benchmarks.
Fill in the correct values for the macros at
the top of the file before executing.
'''
import os

WALA_CORE_JAR = "tools/wala/com.ibm.wala.core-1.5.5.jar"
WALA_SHRIKE_JAR = "tools/wala/com.ibm.wala.shrike-1.5.5.jar"
WALA_UTIL_JAR = "tools/wala/com.ibm.wala.util-1.5.5.jar"
DRIVER_PROGRAM = "tools/wala/driver/ReachableMethods.java"
BENCHMARKS_FOLDER = "../final_dataset"
RESULTS_FOLDER = "wala_results"
JAVAC_COMMAND = "javac"
JAVA_COMMAND = "java"
TIMEOUT = 1800
TIMEOUT_CMD = "timeout"
FILE_WITH_MAIN_CLASS = "info/mainclassname"
SKIP_COMPLETED = True #skips if the output file is already there.

#create the output folder if it doesn't exist
results_folder_path = os.path.join(os.getcwd(),RESULTS_FOLDER)
if not os.path.exists(results_folder_path):
    os.mkdir(results_folder_path)


#add wala jars to classpath. Also add current folder
class_path_string = f'{WALA_CORE_JAR}:{WALA_SHRIKE_JAR}:{WALA_UTIL_JAR}:.'
os.environ["CLASSPATH"] = class_path_string
os.system("echo $CLASSPATH")  

#compile the wala driver program. Ensure Java 8 is used.
compile_command = JAVAC_COMMAND + " " + DRIVER_PROGRAM
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

  #execute wala on the jar
  wala_command = (TIMEOUT_CMD 
    + " " + str(TIMEOUT)
    + " " + JAVA_COMMAND
    + " " + DRIVER_PROGRAM[:-5] #skip the .java part
    + " -classpath"
    + " " + jarfile_path
    + " -mainclass"
    + " " + mainclass_name
    + " > " +  results_folder_path
    + "/" + benchmark + ".txt"
  )
  os.system(wala_command)
