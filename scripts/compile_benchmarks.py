import os
import shutil

BENCHMARKS_FOLDER = "../june2020_dataset"

JAVAC = "javac"
COMPILED_CLASSES_FOLDER = "classes"
LIB_FOLDER = "lib"
SRC_FILES = "src_files.txt"
JAR_FILE_FOLDER = "jarfile"
EXPORTS_PATH = "info/add_exports"

for benchmark in os.listdir(BENCHMARKS_FOLDER):
    if not os.path.isdir(os.path.join(BENCHMARKS_FOLDER, benchmark)):
        continue
    print("Compiling benchmark:", benchmark)
    compiled_classes_path = os.path.join(
        BENCHMARKS_FOLDER, benchmark, COMPILED_CLASSES_FOLDER)
    if not os.path.exists(compiled_classes_path):
        os.mkdir(compiled_classes_path)
    class_path = os.path.join(BENCHMARKS_FOLDER, benchmark, LIB_FOLDER)
    find_srcs_command = f'find {BENCHMARKS_FOLDER}/{benchmark}/src -name "*.java" > {SRC_FILES}'
    os.system(find_srcs_command)
    exports_file_path = os.path.join(
        BENCHMARKS_FOLDER, benchmark, EXPORTS_PATH)
    exports = ""
    if os.path.exists(exports_file_path):
        with open(exports_file_path, "r") as f:
            exports = f.read().strip()
    javac_command = (
        f"{JAVAC} {exports} -g -d {compiled_classes_path} "
        f"-cp {class_path} "
        f"@{SRC_FILES}"
    )

    os.system(javac_command)

    temp_dir = os.path.join(BENCHMARKS_FOLDER, benchmark, "temp")
    os.makedirs(temp_dir, exist_ok=True)
    os.system(f"cp -r {compiled_classes_path}/* {temp_dir}")
    os.system(f"cp -r {class_path}/* {temp_dir}")

    jar_file_path = os.path.join(
        BENCHMARKS_FOLDER, benchmark, JAR_FILE_FOLDER, benchmark + ".jar")
    jar_command = f"jar cf {jar_file_path} -C {temp_dir} ."

    os.system(jar_command)

    shutil.rmtree(temp_dir)
    os.remove(SRC_FILES)

    print(f"Benchmark {benchmark} compiled successfully.")
    print("--------------------------------------------------\n")