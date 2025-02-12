CREATE SCHEMA `chat` ;

USE `chat`;

CREATE TABLE `user` (
    `user_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `phone_number` VARCHAR(15) NOT NULL UNIQUE,
    `display_name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `password_hash` VARCHAR(255) NOT NULL,
    `profile_picture_path` VARCHAR(255),
    `gender` ENUM('male', 'female') NOT NULL DEFAULT 'male',
    `country` VARCHAR(255) NOT NULL,
    `date_of_birth` DATE NOT NULL,
    `bio` VARCHAR(255),
    `status` ENUM('available', 'busy', 'away', 'offline') NOT NULL DEFAULT 'available',
    `last_seen` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `invitation` (
    `invitation_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `sender_id` INT UNSIGNED NOT NULL,
    `receiver_id` INT UNSIGNED NOT NULL,
    `status` ENUM('pending', 'accepted', 'rejected', 'blocked') NOT NULL DEFAULT 'pending',
    FOREIGN KEY (`sender_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`receiver_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
);


CREATE TABLE `user_connection` (
    `user_id` INT UNSIGNED NOT NULL,
    `connected_user_id` INT UNSIGNED NOT NULL,
    `relationship` ENUM('friend', 'family', 'work', 'other') NOT NULL DEFAULT 'friend',
    PRIMARY KEY (`user_id`, `connected_user_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`connected_user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
);


CREATE TABLE `user_blocked_connection` (
    `blocker_user_id` INT UNSIGNED NOT NULL,
    `blocked_user_id` INT UNSIGNED NOT NULL,
    PRIMARY KEY (`blocker_user_id`, `blocked_user_id`),
    FOREIGN KEY (`blocker_user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`blocked_user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
);


CREATE TABLE `direct_message` (
    `message_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `sender_id` INT UNSIGNED NOT NULL,
    `receiver_id` INT UNSIGNED NOT NULL,
    `message_content` TEXT NOT NULL,
    `font_style` VARCHAR(255),
    `font_color` VARCHAR(255),
    `text_background` VARCHAR(255),
    `font_size` INT,
    `is_bold` BOOLEAN DEFAULT FALSE,
    `is_italic` BOOLEAN DEFAULT FALSE,
    `is_underlined` BOOLEAN DEFAULT FALSE,
    `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`sender_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`receiver_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
);


CREATE TABLE `group` (
    `group_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `group_name` VARCHAR(255) NOT NULL,
    `created_by` INT UNSIGNED NOT NULL,
    `created_when` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`created_by`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
);

CREATE TABLE `user_groups` (
    `user_id` INT UNSIGNED NOT NULL,
    `group_id` INT UNSIGNED NOT NULL,
    PRIMARY KEY (`user_id`, `group_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`group_id`) REFERENCES `group`(`group_id`) ON DELETE CASCADE
);

CREATE TABLE `group_message` (
    `message_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `sender_id` INT UNSIGNED NOT NULL,
    `group_id` INT UNSIGNED NOT NULL,
    `message_content` TEXT NOT NULL,
    `font_style` VARCHAR(255),
    `font_color` VARCHAR(255),
    `text_background` VARCHAR(255),
    `font_size` INT,
    `is_bold` BOOLEAN DEFAULT FALSE,
    `is_italic` BOOLEAN DEFAULT FALSE,
    `is_underlined` BOOLEAN DEFAULT FALSE,
    `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`sender_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`group_id`) REFERENCES `group`(`group_id`) ON DELETE CASCADE
);

CREATE TABLE `file_transfer` (
    `file_id` CHAR(36) NOT NULL PRIMARY KEY,  -- UUID (36 characters)
    `sender_id` INT UNSIGNED NOT NULL,
    `receiver_id` INT UNSIGNED NULL,         -- Nullable for group messages
    `group_id` INT UNSIGNED NULL,            -- Added for group support
    `file_name` VARCHAR(255) NOT NULL,
    `file_type` VARCHAR(255) NOT NULL,
    `file_path` VARCHAR(255) NOT NULL,
    `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHECK (
        (`receiver_id` IS NOT NULL AND `group_id` IS NULL) OR
        (`receiver_id` IS NULL AND `group_id` IS NOT NULL)
    )
);

CREATE TABLE `server_announcement` (
    `announcement_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `message` TEXT NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `chatbot` (
    `chatbot_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT UNSIGNED NOT NULL,
    `is_enabled` BOOLEAN DEFAULT FALSE,
    `chatbot_type` ENUM('cleverbot', 'pandorabots', 'jabberwacky', 'aiml') NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
);

CREATE TABLE `social_network` (
    `social_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT UNSIGNED NOT NULL,
    `platform` ENUM('facebook', 'twitter', 'linkedin') NOT NULL,
    `access_token` VARCHAR(255) NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
);


CREATE TABLE `Admin`(
    `admin_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_name` VARCHAR(255) NOT NULL,
    `phone_number` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `password_hash` VARCHAR(255) NOT NULL,
    `gender` ENUM('male', 'female') NOT NULL DEFAULT 'male',
    `country` VARCHAR(255) NOT NULL,
    `date_of_birth` DATE NOT NULL
);
