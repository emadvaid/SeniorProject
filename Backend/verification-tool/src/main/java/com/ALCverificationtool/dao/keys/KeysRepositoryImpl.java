package com.ALCverificationtool.dao.keys;

import com.ALCverificationtool.models.LangRec;
import com.ALCverificationtool.models.TranslationResourceRec;
import com.ALCverificationtool.services.ServiceException;
import com.ALCverificationtool.services.userService.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class KeysRepositoryImpl implements KeysRepository {

    private static final String DROP_SQL =
            "DROP TABLE IF EXISTS `TABLE_NAME`";

    private static final String CREATE_SQL =
            "CREATE TABLE `TABLE_NAME` ( " +
            "  `approved` tinyint(4) NOT NULL," +
            "  `file_name` varchar(255) DEFAULT NULL," +
            "  `file_notes` varchar(255) DEFAULT NULL," +
            "  `folder_path` varchar(255) DEFAULT NULL," +
            "  `key_name` varchar(255) NOT NULL," +
            "  `key_new` tinyint(4) NOT NULL," +
            "  `key_note` varchar(255) DEFAULT NULL," +
            "  `key_variant` varchar(2000) DEFAULT NULL," +
            "  `section_id` varchar(255) DEFAULT NULL," +
            "  `section_note` varchar(255) DEFAULT NULL," +
            "  `key_id` bigint(20) NOT NULL," +
            "  PRIMARY KEY (`key_id`)" +
            ") ENGINE=MyISAM DEFAULT CHARSET=utf8";


    private static final String INSERT_SQL =
            "INSERT INTO `alksudb`.`english_current`\n" +
            "(`approved`,`file_name`,`file_notes`,`folder_path`,\n" +
            "`key_name`,`key_new`,`key_note`,`key_variant`,\n" +
            "`section_id`,`section_note`)\n" +
            "VALUES (?,?,?,?,?,?,?,?,?,?);\n";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public boolean createKeyTable(String keyLanguageVersion, String keyLanguageCode, boolean dropExisting) {

        String newTableName= keyLanguageCode + "_" + keyLanguageVersion;
        System.out.println(DROP_SQL.replace("TABLE_NAME", newTableName));
        System.out.println(CREATE_SQL.replace("TABLE_NAME", newTableName));

        // first make sure that keyLanguageCode and keyLanguageVersion are not null and valid
        if(keyLanguageCode == null && keyLanguageVersion == null) {
            throw new ServiceException("keyLanguageCode & keyLanguageVersion");
        }

        // if dropExisting
        if(true) {
            // then drop any existing table
            jdbcTemplate.update(DROP_SQL.replace("TABLE_NAME", newTableName));
            jdbcTemplate.update(CREATE_SQL.replace("TABLE_NAME", newTableName));

            // make sure the table exists

            return true;
        }

        else {
            // check to see if table exists
            if(true) {
                return true;
            }
        }
        // create new table with the the given tablename


        // check if successfull
        if(true) {
            return true;
        }

        return false;
    }

    @Override
    public TranslationResourceRec create(TranslationResourceRec keyData) {

        // first check that keyData is valid and not not
        if(keyData == null) {
            throw new UserException("key Data is not valid");
        }

        String tableName = toTableName(keyData.getLanguageCode(), keyData.getLanguageVersion());

        // first check if the table for this key is created
       try {
           //jdbcTemplate.query("Select 1 FROM " + tableName + " LIMIT 1 ", resultSet -> {});
           jdbcTemplate.query("Select 1 FROM ? LIMIT 1 ", resultSet -> {},
                   tableName);
           // table is created.
       } catch(Exception e){
           // table is not created.
           throw new ServiceException("tried to insert into table that does not exist");
       }

        // add entry to the correct table

        int nrows = jdbcTemplate.update(INSERT_SQL,
                keyData.getApproved(),keyData.getFileName(),keyData.getFileNotes(),
                keyData.getFolderPath(), keyData.getKeyName(), keyData.getKeyNew(),
                keyData.getKeyNote(), keyData.getKeyVariant(), keyData.getSectionId(), keyData.getSectionNote());

        // check that the insert worked
        if(nrows < 1 ) {
            throw new ServiceException("insert failed");
        }

        // using that key retrieve the keyRec from the database

        // validate the created record

        // return the created record
        return null;
    }
    private String toTableName(String keyLanguageCode, String keyLanguageVersion) {

        return keyLanguageCode + "_" + keyLanguageVersion;
    }
}