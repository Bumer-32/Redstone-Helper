import json
import pyperclip

with open("crowdin/template/RedstoneHelperLocalization.json", "r") as file:
    data = json.load(file)

keys = data.keys()

text = ""

for key in keys:
    if key.startswith("redstone-helper"):
        if not "funny" in key:
            text = text + "const val " + key.replace("redstone-helper.", "").replace("dontlocalize.", "").replace("_", "").replace(".", "_").upper() +" = \"" + key + "\"\n"

print(text)

pyperclip.copy(text)