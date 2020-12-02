import json

with open('pokedex.json') as json_file:
    data = json.load(json_file)
    moves_seen = set()
    output = ''
    for pokemon in data:
        for move in pokemon["Moves"]:
            output += move + ',' + pokemon["Id"] + '\n'
    with open('learnableMove.csv', 'w') as outfile:
        outfile.write(output)