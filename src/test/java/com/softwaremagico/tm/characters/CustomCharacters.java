package com.softwaremagico.tm.characters;

/*-
 * #%L
 * Think Machine The Last Week
 * %%
 * Copyright (C) 2019 Softwaremagico
 * %%
 * This work is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> Valencia (Spain).
 *  
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 *  
 * If you want to read more about this license, please see <https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode>.
 * #L%
 */

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.itextpdf.text.DocumentException;
import com.softwaremagico.tm.InvalidXmlElementException;
import com.softwaremagico.tm.character.CharacterPlayer;
import com.softwaremagico.tm.character.Gender;
import com.softwaremagico.tm.character.Name;
import com.softwaremagico.tm.character.RandomizeCharacter;
import com.softwaremagico.tm.character.Surname;
import com.softwaremagico.tm.character.benefices.AvailableBeneficeFactory;
import com.softwaremagico.tm.character.benefices.BeneficeAlreadyAddedException;
import com.softwaremagico.tm.character.blessings.BlessingAlreadyAddedException;
import com.softwaremagico.tm.character.blessings.BlessingFactory;
import com.softwaremagico.tm.character.blessings.TooManyBlessingsException;
import com.softwaremagico.tm.character.characteristics.CharacteristicName;
import com.softwaremagico.tm.character.creation.CostCalculator;
import com.softwaremagico.tm.character.creation.FreeStyleCharacterCreation;
import com.softwaremagico.tm.character.factions.FactionsFactory;
import com.softwaremagico.tm.character.planets.PlanetFactory;
import com.softwaremagico.tm.character.races.RaceFactory;
import com.softwaremagico.tm.character.skills.AvailableSkillsFactory;
import com.softwaremagico.tm.configurator.MachinePdfConfigurationReader;
import com.softwaremagico.tm.file.PathManager;
import com.softwaremagico.tm.party.Party;
import com.softwaremagico.tm.pdf.complete.PartySheet;
import com.softwaremagico.tm.pdf.small.SmallPartySheet;
import com.softwaremagico.tm.random.exceptions.DuplicatedPreferenceException;
import com.softwaremagico.tm.random.exceptions.InvalidRandomElementSelectedException;
import com.softwaremagico.tm.random.selectors.CombatPreferences;

@Test(groups = "customCharacters")
public class CustomCharacters {
	private static final String LANGUAGE = "es";
	private static final String MODULE_FOLDER = "The Last Week";

	private Party party;

	private void setDescription(CharacterPlayer characterPlayer, String description, String background,
			String specialRules, String secondaryObjectives) {
		characterPlayer.getInfo().setCharacterDescription(description);
		characterPlayer.getInfo().setBackgroundDecription(background + "\n\nReglas Especiales: " + specialRules
				+ "\n\nObjetivo Secundario: " + secondaryObjectives);
	}

	@BeforeClass
	public void initialize() {
		party = new Party(LANGUAGE, PathManager.DEFAULT_MODULE_FOLDER);
		party.setPartyName("LastWeek");
		MachinePdfConfigurationReader.getInstance().setSmallPdfShieldEnabled(false);
		MachinePdfConfigurationReader.getInstance().setSmallPdfBlessingNameEnabled(true);
	}

	@Test
	public void geek() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().setGender(Gender.MALE);
		characterPlayer.getInfo().setAge(35);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("asian", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(8);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(8);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(4);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(3);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(4);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("computing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lore", "computingLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("electronics",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("landcarft",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);

		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("hacker", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("disrespecful",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("clumsy", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros2000]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("highPerformanceComputer",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		System.out.println("Geek " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.PEACEFUL);
		randomizeCharacter.createCharacter();

		String description = "";
		String background = "";
		String specialRules = "";
		String secondaryObjectives = "";

		setDescription(characterPlayer, description, background, specialRules, secondaryObjectives);

		party.addMember(characterPlayer);
	}

	@Test
	public void bill() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().setGender(Gender.MALE);
		characterPlayer.getInfo().setAge(68);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("englishSpeaking",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.getInfo().addName(new Name("Bill", characterPlayer.getLanguage(),
				characterPlayer.getModuleName(), Gender.MALE, characterPlayer.getFaction()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(4);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(4);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("aircraft",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("leadership",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lore", "weaponsLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("warfare",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("mechanics",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 2);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("slugGuns",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("artillery",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("gunnery",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("melee",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("survival",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("selfControl",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);

		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("disciplined",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("bluster", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("missingLeg",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros0]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("typicalMed.Autofeed",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		System.out.println("Bill " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.BELLIGERENT);
		randomizeCharacter.createCharacter();

		String description = "";
		String background = "";
		String specialRules = "";
		String secondaryObjectives = "";

		setDescription(characterPlayer, description, background, specialRules, secondaryObjectives);

		party.addMember(characterPlayer);
	}

	@Test
	public void piousGirl() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().setGender(Gender.FEMALE);
		characterPlayer.getInfo().setAge(31);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("mediterranean",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(4);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(3);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(4);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(8);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("selfControl",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lore", "religion",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("landcarft",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 2);

		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("disciplined",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("pious", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("brainwashed",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("poorLiar", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("painSensitive",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros0]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		System.out.println("Pious " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.PEACEFUL);
		randomizeCharacter.createCharacter();

		String description = "";
		String background = "";
		String specialRules = "";
		String secondaryObjectives = "";

		setDescription(characterPlayer, description, background, specialRules, secondaryObjectives);

		party.addMember(characterPlayer);
	}

	@Test
	public void sportGirl() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().setGender(Gender.FEMALE);
		characterPlayer.getInfo().setAge(32);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("russian", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(8);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(4);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(4);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 8);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("athletics",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("landcarft",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);

		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("tall", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros0]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		System.out.println("SportGirl " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.PEACEFUL);
		randomizeCharacter.createCharacter();

		String description = "";
		String background = "";
		String specialRules = "";
		String secondaryObjectives = "";

		setDescription(characterPlayer, description, background, specialRules, secondaryObjectives);

		party.addMember(characterPlayer);
	}

	@Test
	public void bandGirl() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().setGender(Gender.FEMALE);
		characterPlayer.getInfo().setAge(39);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("mediterranean",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(4);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(5);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("landcarft",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("mechanics",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);

		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("crackPilot",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros50]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("typicalLightRevolver",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("addiction_3",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		System.out.println("BandGirl " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.PEACEFUL);
		randomizeCharacter.createCharacter();

		String description = "";
		String background = "";
		String specialRules = "";
		String secondaryObjectives = "";

		setDescription(characterPlayer, description, background, specialRules, secondaryObjectives);

		party.addMember(characterPlayer);
	}

	@Test
	public void veterinary() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().setGender(Gender.FEMALE);
		characterPlayer.getInfo().setAge(31);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("arabian", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(6);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("ride",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lore", "beastsLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("computing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 2);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("landcarft",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("medicine",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);

		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("tall", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("clumsy", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros0]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		System.out.println("Vet " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.PEACEFUL);
		randomizeCharacter.createCharacter();

		String description = "";
		String background = "";
		String specialRules = "";
		String secondaryObjectives = "";

		setDescription(characterPlayer, description, background, specialRules, secondaryObjectives);

		party.addMember(characterPlayer);
	}

	@Test
	public void nerd() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().setGender(Gender.MALE);
		characterPlayer.getInfo().setAge(21);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("mediterranean",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(4);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(5);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lore", "undead",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lore", "demons",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lore", "fairies",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("computing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);

		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("curious", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("delusional",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("gullible", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("painSensitive",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("clumsy", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros0]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		System.out.println("Nerd " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.PEACEFUL);
		randomizeCharacter.createCharacter();

		String description = "";
		String background = "";
		String specialRules = "";
		String secondaryObjectives = "";

		setDescription(characterPlayer, description, background, specialRules, secondaryObjectives);

		party.addMember(characterPlayer);
	}

	@Test
	public void teacher() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().setGender(Gender.MALE);
		characterPlayer.getInfo().setAge(28);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("mediterranean",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(4);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("computing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("landcarft",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);

		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("handsome", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("casanova", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("badLiver", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("cad", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("language [gracefulTongue]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros50]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		System.out.println("Teacher " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.PEACEFUL);
		randomizeCharacter.createCharacter();

		characterPlayer.getInfo().setCharacterDescription(characterPlayer.getCompleteNameRepresentation()
				+ " es un hombre con buena presencia y saber vestir. Trabaja como profesor de instituto y hace gala de conocimientos en varias materias.");
		characterPlayer.getInfo().setBackgroundDecription("Durante la última semana, Borracho");

		party.addMember(characterPlayer);
	}

	@Test
	public void cartomancer() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().setGender(Gender.FEMALE);
		characterPlayer.getInfo().setAge(51);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("arabian", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(8);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(4);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(4);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("gaming",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lockpicking",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("streetWise",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sleightOfHand",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("knavery",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);

		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("eloquent", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros1000]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		System.out.println("Cartomancer " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.PEACEFUL);
		randomizeCharacter.createCharacter();

		String description = "";
		String background = "";
		String specialRules = "";
		String secondaryObjectives = "";

		setDescription(characterPlayer, description, background, specialRules, secondaryObjectives);

		party.addMember(characterPlayer);
	}

	@Test
	public void dwarf() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().addName(new Name("Peter", characterPlayer.getLanguage(),
				characterPlayer.getModuleName(), Gender.MALE, characterPlayer.getFaction()));
		characterPlayer.getInfo().setSurname(new Surname("Dinklage", characterPlayer.getLanguage(),
				characterPlayer.getModuleName(), characterPlayer.getFaction()));

		characterPlayer.getInfo().setGender(Gender.MALE);
		characterPlayer.getInfo().setAge(78);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("englishSpeaking",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(4);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(8);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(4);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 8);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("melee",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 2);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("archery",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 2);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("arts", "music",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("disguise",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("performance",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 8);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("etiquette",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("leadership",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);

		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros5000]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("dwarf", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("wellLiked", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		System.out.println("Dwarf " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.PEACEFUL);
		randomizeCharacter.createCharacter();

		String description = "";
		String background = "";
		String specialRules = "";
		String secondaryObjectives = "";

		setDescription(characterPlayer, description, background, specialRules, secondaryObjectives);

		party.addMember(characterPlayer);
	}

	@AfterClass
	public void createPartySheet() {
		final PartySheet sheet = new PartySheet(party);
		Assert.assertEquals(
				sheet.createFile(System.getProperty("java.io.tmpdir") + File.separator + party.getPartyName() + ".pdf"),
				18);

		final SmallPartySheet smallSheet = new SmallPartySheet(party);
		Assert.assertEquals(
				smallSheet.createFile(
						System.getProperty("java.io.tmpdir") + File.separator + party.getPartyName() + "_Small.pdf"),
				9);
	}
}
