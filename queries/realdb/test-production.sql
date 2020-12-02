-- RUN: db2 -stvf test-production.sql > test-production.out

-- Initialize a trainer
INSERT INTO Trainer (id, name, gender) VALUES (1, 'foo', 'M');

-- Add a pokemon for trainer 1 that is in and not in party
INSERT INTO OwnedPokemon (trainer_id, pkmn_id, nickname, in_team, m1, ability) VALUES (1, 1, 'bar', True, 'Tackle', 'Overgrow');
INSERT INTO OwnedPokemon (trainer_id, pkmn_id, nickname, in_team, m1, ability) VALUES (1, 1, 'bar2', False, 'Tackle', 'Overgrow');
-- Add a pokemon with no nickname
INSERT INTO OwnedPokemon (trainer_id, pkmn_id, nickname, in_team, m1, ability) VALUES (1, 1, '', False, 'Tackle', 'Overgrow');
-- Add a pokemon with full moveset
INSERT INTO OwnedPokemon (trainer_id, pkmn_id, nickname, in_team, m1, m2, m3, m4, ability) VALUES (1, 1, 'bar3', False, 'Tackle', 'Growl', 'Vine Whip', 'Poison Powder','Overgrow');

-- Verify that new pokemon were added
SELECT trainer_id, nickname, Pokemon.name as name, m1, ability FROM OwnedPokemon JOIN Pokemon ON OwnedPokemon.pkmn_id=Pokemon.id WHERE trainer_id=1 AND in_team=True;
SELECT trainer_id, nickname, Pokemon.name as name, m1, ability FROM OwnedPokemon JOIN Pokemon ON OwnedPokemon.pkmn_id=Pokemon.id WHERE trainer_id=1 AND in_team=False;

SELECT m1, m2, m3, m4, ability, nickname FROM OwnedPokemon;

-- Update pokemon move, ability, and nickname
Update OwnedPokemon Set m1 = 'Seed Bomb', ability = 'Chlorophyll', nickname='bar3' WHERE nickname = 'bar';
-- Check that move, ability and nickname were updated
Select nickname, m1, ability FROM OwnedPokemon WHERE nickname ='bar3';

-- Remove pokemon from team
Update OwnedPokemon Set in_team = False WHERE nickname = 'bar3';
-- Check that pokemon was removed from team
Select nickname from OwnedPokemon WHERE in_team = False;

-- Cleanup
DELETE FROM Trainer WHERE name='foo';
-- RUN: db2 -stvf test-production.sql > test-production.out

-- a set of queries to test searching pokemon feature

-- Search by Name

SELECT id, name, type1, type2, category, capture_rate, hp, atk FROM Pokemon WHERE UPPER(name) = UPPER('pikachu');

-- Search by ID
SELECT id, name, type1, type2, category, capture_rate, hp, atk FROM Pokemon WHERE ID = 25;

-- Search by Type
-- ID     NAME         TYPE1        TYPE2        CATEGORY                  CAPTURE_RATE HP     ATK 
SELECT id, name, type1, type2, category, capture_rate, hp, atk FROM Pokemon WHERE UPPER(type1) = UPPER('grass') or UPPER(type2) = UPPER('grass');

-- Initialize a trainer
INSERT INTO Trainer (id, name, gender) VALUES (1, 'foo', 'M');

-- Add a pokemon for trainer 1 that is in and not in party
INSERT INTO OwnedPokemon (trainer_id, pkmn_id, nickname, in_team, m1, ability) VALUES (1, 1, 'bar', True, 'Tackle', 'Overgrow');
INSERT INTO OwnedPokemon (trainer_id, pkmn_id, nickname, in_team, m1, ability) VALUES (1, 1, 'bar2', False, 'Tackle', 'Overgrow');

-- Verify that new pokemon were added
SELECT trainer_id, nickname, Pokemon.name as name, m1, ability FROM OwnedPokemon JOIN Pokemon ON OwnedPokemon.pkmn_id=Pokemon.id WHERE trainer_id=1 AND in_team=True;
SELECT trainer_id, nickname, Pokemon.name as name, m1, ability FROM OwnedPokemon JOIN Pokemon ON OwnedPokemon.pkmn_id=Pokemon.id WHERE trainer_id=1 AND in_team=False;


SELECT m1, m2, m3, m4, ability, nickname FROM OwnedPokemon;

-- Update pokemon move, ability, and nickname
Update OwnedPokemon Set m1 = 'Seed Bomb', ability = 'Chlorophyll', nickname='bar3' WHERE nickname = 'bar';
-- Check that move, ability and nickname were updated
Select nickname, m1, ability FROM OwnedPokemon WHERE nickname ='bar3';

-- Remove pokemon from team
Update OwnedPokemon Set in_team = False WHERE nickname = 'bar3';
-- Check that pokemon was removed from team
Select nickname from OwnedPokemon WHERE in_team = False;

-- Cleanup
DELETE FROM Trainer WHERE name='foo';