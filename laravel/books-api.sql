-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 09, 2025 at 08:36 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `books-api`
--

-- --------------------------------------------------------

--
-- Table structure for table `authors`
--

CREATE TABLE `authors` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `biografi` text DEFAULT NULL,
  `birth_date` date NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `authors`
--

INSERT INTO `authors` (`id`, `name`, `biografi`, `birth_date`, `created_at`, `updated_at`) VALUES
(1, 'Wirda Suryatmi', 'Recusandae tenetur necessitatibus ut qui molestiae dicta unde consequatur. Et quod voluptatem ut nihil cumque culpa dolores tempora. Architecto non omnis laborum. Provident porro nostrum expedita.', '1995-06-14', '2025-06-08 21:10:01', '2025-06-08 21:10:01'),
(2, 'Talia Puspasari', 'Autem voluptatibus sunt possimus ex praesentium laudantium. Perferendis iste aut omnis sit ut numquam. Autem omnis iusto provident corporis natus voluptas. Tempore rerum exercitationem in id fuga.', '2007-10-31', '2025-06-08 21:10:01', '2025-06-08 21:10:01'),
(3, 'Jumari Vega Iswahyudi S.H.', 'Reiciendis et a voluptate inventore facilis reiciendis fugit porro. Enim nulla optio nesciunt corrupti rerum. Expedita odit vero repudiandae aut qui ipsum incidunt labore.', '1987-10-14', '2025-06-08 21:10:01', '2025-06-08 21:10:01'),
(4, 'Hani Suryatmi', 'Quibusdam dolor architecto et. Eum nulla consequatur iusto quia distinctio magni. Vel dolor fuga perferendis nisi harum.', '2001-09-04', '2025-06-08 21:10:01', '2025-06-08 21:10:01'),
(5, 'Nova Shakila Susanti S.H.', 'Nihil consequatur ducimus impedit voluptas et. Numquam et voluptas omnis qui. Quam cum hic eveniet nesciunt eius perferendis. Vitae earum quo vitae itaque. Mollitia at laboriosam earum veritatis.', '2012-11-19', '2025-06-08 21:10:01', '2025-06-08 21:10:01');

-- --------------------------------------------------------

--
-- Table structure for table `buku`
--

CREATE TABLE `buku` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `judul` varchar(255) NOT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `tanggal_publikasi` date NOT NULL,
  `deskripsi` text DEFAULT NULL,
  `rating` decimal(2,1) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `author_id` bigint(20) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `buku`
--

INSERT INTO `buku` (`id`, `judul`, `genre`, `tanggal_publikasi`, `deskripsi`, `rating`, `created_at`, `updated_at`, `author_id`) VALUES
(1, 'Quis a quae.', 'Roman', '1970-06-19', 'Unde occaecati vitae vel placeat deleniti. Aliquid quo iure veritatis dolores. Numquam similique ut delectus facilis quidem consequatur.\n\nVoluptatem ipsum sint laboriosam culpa suscipit et eius accusantium. Fuga nostrum illo aperiam similique eligendi aliquam. Blanditiis dolores voluptas consequatur eos. Exercitationem unde modi voluptas maxime aut vero. Culpa aut sit ipsa repudiandae praesentium qui.\n\nNemo ad dolores voluptatibus et ut. Ratione quis maiores aut fugit quo. Nobis quo necessitatibus maiores tempore.', 4.1, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 5),
(2, 'Sunt assumenda et iusto et.', 'Komedi', '1998-10-15', 'Nihil voluptatem temporibus et commodi corporis natus quod sunt. At vel repellat et repellendus distinctio nam quisquam.\n\nVitae ratione iusto quas quasi. Culpa molestiae et ex maiores repellendus. Consequatur odit delectus repellat placeat vero labore minus ab.\n\nQuos facere quas est eveniet odio. Eum temporibus veniam dolor et voluptatem. Asperiores labore vero officia qui.', 3.8, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 1),
(3, 'Aliquid qui saepe dolorem impedit sequi.', 'Thriller', '1992-10-02', 'Ab et nihil voluptatem ipsa dolor eos ut. Ratione ab commodi rerum dolores maiores. Itaque eum aliquid qui vel consequatur. Rerum eligendi eligendi inventore voluptatum fuga itaque voluptatem. Qui est et quis pariatur voluptatem id.\n\nVeniam velit labore modi est magni maxime sit. Est voluptas sequi voluptatem aliquid praesentium. Sint cupiditate est consequatur voluptas expedita quas qui.\n\nEst adipisci error repellat et. Enim voluptates reiciendis ut saepe. Et sed saepe aut temporibus.', 3.4, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 3),
(4, 'Natus fugit debitis voluptatem.', 'Fantasi', '2023-09-11', 'Perspiciatis in dolore cum corporis nihil. Maxime quia officia aut sequi. Inventore ea voluptas aliquid.\n\nEnim earum vero iste incidunt. Repellendus aliquid dolorum fugiat quisquam et. Magni animi consectetur quod minus.\n\nConsequuntur expedita numquam ut deserunt asperiores quo. Quae eius blanditiis rerum et deserunt. Eius quo non voluptatibus.', 2.4, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 1),
(5, 'Reprehenderit sint.', 'Misteri', '1980-10-10', 'Corrupti quo cum commodi repellat ut quia. Sed veritatis quia doloremque harum eos voluptate facilis. Vero aut delectus rem ea autem.\n\nDolore eum nostrum id consequuntur ex et odit perspiciatis. Temporibus autem quisquam hic fuga voluptatem fugiat. Quis qui illo sed hic laboriosam nobis illum. Et ullam voluptas facilis impedit.\n\nDebitis ut nemo laboriosam quisquam rerum. Sint perferendis est sed deleniti. Officiis et laboriosam asperiores consequatur. Repellendus commodi non ipsum dignissimos aliquam in. Aut eius culpa reiciendis maiores earum distinctio fugit.', 2.1, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 1),
(6, 'Quis eaque qui doloribus quaerat.', 'Misteri', '1984-02-28', 'Et quo autem assumenda maxime quos eos iste. Aut nihil rerum error quia minus. Quos veniam explicabo sit id est.\n\nEa omnis qui aliquam sunt ea sed commodi et. Et aut iste recusandae non tempora. Reprehenderit occaecati voluptates porro placeat aut. Temporibus quasi numquam culpa qui nam.\n\nDignissimos quaerat rerum molestias eum est. Fuga inventore numquam iusto. Ducimus ipsa odit dolore.', 2.5, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 5),
(7, 'Consequatur fugit sit.', 'Sains Fiksi', '1999-11-28', 'Ullam animi et blanditiis consequatur eos. Quam sed magnam quia tenetur. Aliquid in deserunt ad sint sed quis. Maxime id quas repellendus totam inventore adipisci quia dolor. Velit eligendi facilis non est quas.\n\nInventore similique aperiam accusamus. Tenetur omnis blanditiis natus doloribus sequi ipsum voluptas. Esse et laborum quo possimus debitis delectus.\n\nQui corporis eaque consequatur. Iure veritatis sapiente omnis consectetur.', 4.4, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 5),
(8, 'Sit praesentium ab.', 'Misteri', '2023-12-10', 'Nihil illum ut qui rerum maxime nostrum. Odit ab magnam optio rerum. Eum illo eaque nihil ut impedit vero dolorem. Dolorem a maxime odio at. Aut officiis accusantium explicabo occaecati assumenda deleniti aliquam et.\n\nIllo laboriosam dolore quis magnam illo. Est eaque velit consectetur quisquam. Pariatur tempore saepe facere nesciunt numquam neque dolores.\n\nHic corrupti perspiciatis itaque ut. Aut totam et modi error laudantium qui. Soluta officiis aut aliquid iusto sed. Natus totam sit sed totam ex dolores ut.', 4.3, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 4),
(9, 'Dicta doloremque aut sit vero adipisci sequi veniam.', 'Fantasi', '2006-03-25', 'Fuga dolores aut quasi omnis recusandae. Sit error quia et natus aliquam sed. Voluptatem autem ut possimus recusandae modi.\n\nExcepturi autem vel atque voluptatem amet et ullam. Ea tempore rerum minus est similique dolor et doloribus. Iste ex rerum voluptas ad.\n\nQuae explicabo cupiditate occaecati in. Numquam et deleniti sit blanditiis.', 1.0, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 1),
(10, 'Nobis sit omnis necessitatibus.', 'Sejarah', '2017-12-02', 'Perspiciatis corporis sit rerum optio maiores. Sed numquam qui architecto maxime.\n\nEt laborum quos quo ducimus. Ea quia voluptate aliquid voluptate molestiae veniam quas. Neque et eveniet qui. Consectetur perspiciatis officiis saepe fuga nesciunt laudantium.\n\nNihil molestias necessitatibus libero error perferendis hic rem neque. Maiores fuga ipsam expedita et excepturi impedit et praesentium. Repellendus qui et tenetur quis quo commodi.', 1.4, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 5),
(11, 'Cupiditate similique voluptas nam tempore temporibus voluptatum corporis vero.', 'Fantasi', '2005-01-07', 'Excepturi quod quas sit quia. Magnam culpa nemo neque suscipit molestiae quia nulla. Sed et qui aliquam eius sint. Ad et pariatur dolores laborum quos.\n\nQui ut excepturi sed non est. Deserunt accusamus aut optio id laborum porro. Quas aliquid atque eligendi perferendis fuga placeat sint. Qui laboriosam dolorem qui aut.\n\nPariatur vel molestias aut magni libero praesentium et. Aspernatur libero exercitationem vitae quod corrupti maxime laudantium. Sed aut dolorem sit iusto qui numquam adipisci.', 2.2, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 5),
(12, 'Sint qui explicabo quibusdam sunt accusamus.', 'Fantasi', '1998-01-27', 'Possimus facilis at dolorem rem ut. Quia optio hic et aut id voluptas. Quibusdam porro ducimus provident velit. Accusantium nesciunt architecto aut sed dignissimos.\n\nOdio modi minima voluptatibus accusantium ex sit saepe. Magnam reprehenderit veniam iusto vel ea ut maiores.\n\nEt ut praesentium soluta corporis et repudiandae voluptas. Reiciendis iure sit quia ut eveniet aliquam. Unde alias aut non. Odio voluptas ut illo.', 4.6, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 5),
(13, 'Non qui necessitatibus hic et.', 'Fantasi', '2016-02-16', 'Sequi quia ut aliquid reprehenderit. Ea odit dicta ut eveniet.\n\nEt perferendis odio eum ut. Hic labore maiores ut sunt ut. Earum deserunt ullam est nam qui consequatur. Culpa hic qui fuga dolores.\n\nAmet illo sed qui odit. Et doloribus dolorum fugit sed vel. Unde atque eum beatae sed enim natus libero. Voluptatem vero ut voluptatibus cum.', 3.8, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 3),
(14, 'Nobis consequatur vitae.', 'Horor', '2023-06-10', 'Adipisci vitae incidunt ut sit quia. Temporibus velit minima reiciendis consectetur qui sit. Assumenda aut doloremque qui et expedita odio est.\n\nRerum velit nam ex autem nostrum maiores hic deserunt. Ut animi corrupti expedita expedita sunt. Aut commodi laudantium perferendis voluptatem ut.\n\nIllum dolor enim necessitatibus quaerat tenetur corrupti. Et numquam aperiam modi dignissimos.', 2.9, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 5),
(15, 'Rerum blanditiis et natus aut.', 'Fantasi', '1998-06-05', 'Accusantium quia recusandae unde natus ut recusandae. Magni neque eos deserunt quia error rerum. Placeat ducimus sint doloremque.\n\nCupiditate quis magnam dicta quidem natus occaecati quidem. Eum quis repudiandae eos dolores qui voluptatem. Autem quia quia omnis doloribus sit et. Aut quasi hic iusto eveniet nobis iure.\n\nCulpa nihil et magnam corrupti qui porro. Eos similique quas placeat aut. Beatae neque ipsa illum culpa.', 4.6, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 4),
(16, 'Qui voluptas vero asperiores et distinctio maiores.', 'Sejarah', '1995-04-12', 'Voluptatem enim aut est sed. Reprehenderit autem deleniti sed iste vel. Dolor illum facilis consectetur dignissimos sed.\n\nMolestiae rerum laborum inventore expedita expedita et. Et qui debitis autem aperiam maxime omnis. Quia non aut nesciunt repudiandae accusamus ratione dolore.\n\nTotam enim veniam dolores saepe. Est quod et eaque a nisi suscipit ad. Odit praesentium eum sint corporis et illum. Reprehenderit voluptas velit non blanditiis facere.', 4.2, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 1),
(17, 'Error maiores at beatae nulla praesentium deserunt officia.', 'Roman', '1976-01-08', 'Exercitationem eum accusamus praesentium ut quos. Ut aliquid impedit voluptatem voluptates sequi aperiam molestias. Molestiae voluptates doloremque inventore blanditiis veritatis. Ipsam quo soluta rerum commodi veritatis. Eos voluptas molestiae omnis facilis eum.\n\nPlaceat ipsum et fugit earum nesciunt nobis omnis. Voluptas accusantium doloremque adipisci placeat vero.\n\nQui sapiente magnam odio aut. Distinctio officia architecto placeat dolores ut fugiat doloremque distinctio. Placeat natus quo impedit ea laudantium aut.', 3.7, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 2),
(18, 'Esse consequuntur deserunt nobis optio.', 'Sejarah', '2004-06-18', 'Aperiam dolor quia pariatur id et est enim eum. Saepe sunt tempora neque ut. Nemo tenetur consequatur dolorum aliquid et hic. Et sed accusantium nihil consequatur rerum.\n\nNon adipisci nisi dolor est officiis aut possimus. Aliquid perspiciatis qui ullam alias. Cupiditate velit consectetur enim eius ut neque.\n\nEt aut rerum consequuntur dolorum aut itaque. Doloremque eaque ad explicabo eum voluptatem unde. Numquam recusandae eos repellendus distinctio quia.', 4.9, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 2),
(19, 'Corrupti qui accusantium non dolore dolor quia ipsa quod.', 'Roman', '1971-11-06', 'Eos laudantium voluptatibus repudiandae officiis beatae ab. Id itaque optio natus sit. Et nihil qui quis nostrum est in.\n\nRem fugiat incidunt recusandae ipsum. Libero sit distinctio et fuga est. Distinctio alias adipisci sequi ut maxime.\n\nIllo placeat dicta cum earum. Consequatur repellendus odio aliquid natus quis ut. Est accusamus odit autem est est quos.', 4.5, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 5),
(20, 'Rerum commodi sit nihil laboriosam doloribus unde tempore.', 'Sejarah', '1979-07-06', 'Neque consequatur molestias aspernatur voluptatem consequuntur aliquam. Quibusdam maiores eos quasi. Odit sit ipsum odio ullam et rem. Dolorum qui nihil omnis repellendus ut nihil sed. Aut impedit et doloremque et.\n\nRepellendus quis in omnis nihil. Culpa perferendis quae sit adipisci expedita. Voluptates facere cum aperiam facere consequatur sapiente alias. Aut modi id nemo possimus ipsa.\n\nIpsam reprehenderit at quia. Cupiditate voluptas vero dolores. Perspiciatis sit aut nihil nobis. Magni minus facilis amet fugiat assumenda et. Voluptas sed sint sint harum illo.', 3.4, '2025-06-08 21:10:01', '2025-06-08 21:10:01', 1);

-- --------------------------------------------------------

--
-- Table structure for table `cache`
--

CREATE TABLE `cache` (
  `key` varchar(255) NOT NULL,
  `value` mediumtext NOT NULL,
  `expiration` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cache_locks`
--

CREATE TABLE `cache_locks` (
  `key` varchar(255) NOT NULL,
  `owner` varchar(255) NOT NULL,
  `expiration` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `failed_jobs`
--

CREATE TABLE `failed_jobs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `connection` text NOT NULL,
  `queue` text NOT NULL,
  `payload` longtext NOT NULL,
  `exception` longtext NOT NULL,
  `failed_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `jobs`
--

CREATE TABLE `jobs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `queue` varchar(255) NOT NULL,
  `payload` longtext NOT NULL,
  `attempts` tinyint(3) UNSIGNED NOT NULL,
  `reserved_at` int(10) UNSIGNED DEFAULT NULL,
  `available_at` int(10) UNSIGNED NOT NULL,
  `created_at` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `job_batches`
--

CREATE TABLE `job_batches` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `total_jobs` int(11) NOT NULL,
  `pending_jobs` int(11) NOT NULL,
  `failed_jobs` int(11) NOT NULL,
  `failed_job_ids` longtext NOT NULL,
  `options` mediumtext DEFAULT NULL,
  `cancelled_at` int(11) DEFAULT NULL,
  `created_at` int(11) NOT NULL,
  `finished_at` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(255) NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(1, '0001_01_01_000000_create_users_table', 1),
(2, '0001_01_01_000001_create_cache_table', 1),
(3, '0001_01_01_000002_create_jobs_table', 1),
(4, '2025_06_02_080222_create_bukus_table', 1),
(5, '2025_06_09_020905_create_authors_table', 1),
(6, '2025_06_09_021435_modify_buku_table_for_author_and_date', 1),
(7, '2025_06_09_040131_add_genre_description_rating_to_buku_table', 1);

-- --------------------------------------------------------

--
-- Table structure for table `password_reset_tokens`
--

CREATE TABLE `password_reset_tokens` (
  `email` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sessions`
--

CREATE TABLE `sessions` (
  `id` varchar(255) NOT NULL,
  `user_id` bigint(20) UNSIGNED DEFAULT NULL,
  `ip_address` varchar(45) DEFAULT NULL,
  `user_agent` text DEFAULT NULL,
  `payload` longtext NOT NULL,
  `last_activity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `remember_token` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `email_verified_at`, `password`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 'Test User', 'test@example.com', NULL, '$2y$12$CWeYS8tTLpgw6FVJwrmvqulLXUB9Fb6bo4XjjsgyGeijX5FTyPuvu', NULL, '2025-06-08 21:10:01', '2025-06-08 21:10:01');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `authors`
--
ALTER TABLE `authors`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `buku`
--
ALTER TABLE `buku`
  ADD PRIMARY KEY (`id`),
  ADD KEY `buku_author_id_foreign` (`author_id`);

--
-- Indexes for table `cache`
--
ALTER TABLE `cache`
  ADD PRIMARY KEY (`key`);

--
-- Indexes for table `cache_locks`
--
ALTER TABLE `cache_locks`
  ADD PRIMARY KEY (`key`);

--
-- Indexes for table `failed_jobs`
--
ALTER TABLE `failed_jobs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `failed_jobs_uuid_unique` (`uuid`);

--
-- Indexes for table `jobs`
--
ALTER TABLE `jobs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `jobs_queue_index` (`queue`);

--
-- Indexes for table `job_batches`
--
ALTER TABLE `job_batches`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `password_reset_tokens`
--
ALTER TABLE `password_reset_tokens`
  ADD PRIMARY KEY (`email`);

--
-- Indexes for table `sessions`
--
ALTER TABLE `sessions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sessions_user_id_index` (`user_id`),
  ADD KEY `sessions_last_activity_index` (`last_activity`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_unique` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `authors`
--
ALTER TABLE `authors`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `buku`
--
ALTER TABLE `buku`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `failed_jobs`
--
ALTER TABLE `failed_jobs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `jobs`
--
ALTER TABLE `jobs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `buku`
--
ALTER TABLE `buku`
  ADD CONSTRAINT `buku_author_id_foreign` FOREIGN KEY (`author_id`) REFERENCES `authors` (`id`) ON DELETE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
