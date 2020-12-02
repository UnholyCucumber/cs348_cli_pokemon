import json

with open('pokedex.json') as json_file:
    data = json.load(json_file)
    moves_seen = set()
    output = ''
    for pokemon in data:
        for ability in pokemon["Abilities"]:
            output += ability + ', ' + pokemon["Id"]  + '\n'
    with open('learnableAbilities.csv', 'w') as outfile:
        outfile.write(output)