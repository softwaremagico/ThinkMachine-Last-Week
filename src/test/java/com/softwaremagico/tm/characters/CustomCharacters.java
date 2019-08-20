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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.itextpdf.text.DocumentException;
import com.softwaremagico.tm.InvalidXmlElementException;
import com.softwaremagico.tm.character.CharacterPlayer;
import com.softwaremagico.tm.character.Gender;
import com.softwaremagico.tm.character.RandomizeCharacter;
import com.softwaremagico.tm.character.benefices.AvailableBeneficeFactory;
import com.softwaremagico.tm.character.benefices.BeneficeAlreadyAddedException;
import com.softwaremagico.tm.character.blessings.BlessingAlreadyAddedException;
import com.softwaremagico.tm.character.blessings.BlessingFactory;
import com.softwaremagico.tm.character.blessings.TooManyBlessingsException;
import com.softwaremagico.tm.character.characteristics.CharacteristicName;
import com.softwaremagico.tm.character.factions.FactionsFactory;
import com.softwaremagico.tm.character.planets.PlanetFactory;
import com.softwaremagico.tm.character.races.RaceFactory;
import com.softwaremagico.tm.character.skills.AvailableSkillsFactory;
import com.softwaremagico.tm.file.Path;
import com.softwaremagico.tm.language.LanguagePool;
import com.softwaremagico.tm.party.Party;
import com.softwaremagico.tm.pdf.complete.CharacterSheet;
import com.softwaremagico.tm.pdf.small.SmallCharacterSheet;
import com.softwaremagico.tm.random.exceptions.DuplicatedPreferenceException;
import com.softwaremagico.tm.random.exceptions.InvalidRandomElementSelectedException;
import com.softwaremagico.tm.random.selectors.CombatPreferences;

@Test(groups = "customCharacters")
public class CustomCharacters {
	private static final String LANGUAGE = "es";
	private static final String MODULE_FOLDER = "The Last Week";

	private Party party;

	@BeforeClass
	public void initialize() {
		party = new Party(LANGUAGE, Path.DEFAULT_MODULE_FOLDER);
		party.setPartyName("Lost Wonderers");
	}

	@Test
	public void geek() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().setGender(Gender.FEMALE);
		characterPlayer.getInfo().setAge(35);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(
				PlanetFactory.getInstance().getElement("terra", characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("asian", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(3);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(3);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(3);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(3);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(3);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(3);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(3);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(3);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(3);

		characterPlayer.setSkillRank(
				AvailableSkillsFactory.getInstance().getElement("influence", characterPlayer.getLanguage(),
						characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(
				AvailableSkillsFactory.getInstance().getElement("computing", characterPlayer.getLanguage(),
						characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(
				AvailableSkillsFactory.getInstance().getElement("lore", "computingLore", characterPlayer.getLanguage(),
						characterPlayer.getModuleName()), 5);

		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("hacker", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros0]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		LanguagePool.clearCache();
		final CharacterSheet sheet = new CharacterSheet(characterPlayer);
		Assert.assertEquals(sheet.createFile(System.getProperty("java.io.tmpdir") + File.separator + "Geek.pdf"), 2);

		LanguagePool.clearCache();
		final SmallCharacterSheet smallSheet = new SmallCharacterSheet(characterPlayer);
		Assert.assertEquals(
				smallSheet.createFile(System.getProperty("java.io.tmpdir") + File.separator + "Geek_Small.pdf"), 1);

		// Assert.assertEquals(CostCalculator.getCost(player),
		// FreeStyleCharacterCreation.getFreeAvailablePoints(player.getInfo().getAge()));
		// Assert.assertEquals(player.getMoney(), 300);
		
		//Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0, CombatPreferences.PEACEFUL);
		randomizeCharacter.createCharacter();

		party.addMember(characterPlayer);
	}
}
