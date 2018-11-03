UPDATE `alksudb`.`english_current`
SET
`approved` = <{approved: }>,
`file_name` = <{file_name: }>,
`file_notes` = <{file_notes: }>,
`folder_path` = <{folder_path: }>,
`key_name` = <{key_name: }>,
`key_new` = <{key_new: }>,
`key_note` = <{key_note: }>,
`key_variant` = <{key_variant: }>,
`section_id` = <{section_id: }>,
`section_note` = <{section_note: }>
WHERE `key_name` = <{expr}>;
