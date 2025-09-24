import os
import shutil

# the tool requires the use of wala jars.
# use version 1.5.7 for the wala jars.
WALA_JARS_LOCATION = "tools/wala"
WALA_CORE_JAR = "com.ibm.wala.core-1.5.7.jar"
WALA_SHRIKE_JAR = "com.ibm.wala.shrike-1.5.7.jar"
WALA_UTIL_JAR = "com.ibm.wala.util-1.5.7.jar"

RESULTS_FOLDER = "querymax_results"
QUERYMAX_FOLDER = "tools/querymax/code"
BENCHMARKS_FOLDER = "../../datasets/june2020_dataset"
QUERYMAX_OPTIONS = "--algorithm classBudget --analysis castCheck --class_budget 0.03"

#create the output folder if it doesn't exist
results_folder_path = os.path.join(os.getcwd(),RESULTS_FOLDER)
if not os.path.exists(results_folder_path):
    os.mkdir(results_folder_path)

benchmarks_folder_path = os.path.join(os.getcwd(),BENCHMARKS_FOLDER)

#copy wala jars to querymax folder (this is needed according to the querymax documentation)
shutil.copy(f'{WALA_JARS_LOCATION}/{WALA_CORE_JAR}',QUERYMAX_FOLDER)
shutil.copy(f'{WALA_JARS_LOCATION}/{WALA_SHRIKE_JAR}',QUERYMAX_FOLDER)
shutil.copy(f'{WALA_JARS_LOCATION}/{WALA_UTIL_JAR}',QUERYMAX_FOLDER)

cd_querymax_command = f'cd {QUERYMAX_FOLDER}'
mkdir_command = "mkdir -p classes"
javac_command = f'javac -cp {WALA_CORE_JAR}:{WALA_SHRIKE_JAR}:{WALA_UTIL_JAR} -d classes */*.java */*/*.java'
cd_scripts_command = "cd scripts"
python_command = f'python3 run_script.py --benchmarks_folder {benchmarks_folder_path} --results_folder {results_folder_path} {QUERYMAX_OPTIONS}'

full_command = f'{cd_querymax_command} && {mkdir_command} && {javac_command} && {cd_scripts_command} && {python_command}'

print(full_command)
os.system(full_command)
