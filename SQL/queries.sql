/* 1. User Upcoming Events */
SELECT e.event_id, e.title, e.start_date, e.end_date
FROM Events e
JOIN Registrations r ON e.event_id = r.event_id
JOIN Users u ON r.user_id = u.user_id
WHERE e.city = u.city
  AND e.status = 'upcoming'
  AND e.start_date > NOW()
  AND u.user_id = :user_id
ORDER BY e.start_date;

/* 2. Top Rated Events */
SELECT e.event_id, e.title, ROUND(AVG(f.rating),2) as avg_rating
FROM Events e
JOIN Feedback f ON e.event_id = f.event_id
GROUP BY e.event_id, e.title
HAVING COUNT(f.feedback_id) >= 10
ORDER BY avg_rating DESC;

/* 3. Inactive Users */
SELECT u.user_id, u.full_name, u.email
FROM Users u
LEFT JOIN Registrations r ON u.user_id = r.user_id
GROUP BY u.user_id, u.full_name, u.email
HAVING MAX(r.registration_date) < DATE_SUB(CURRENT_DATE(), INTERVAL 90 DAY)
   OR MAX(r.registration_date) IS NULL;

/* 4. Peak Session Hours */
SELECT e.event_id, e.title, COUNT(s.session_id) as morning_sessions
FROM Events e
JOIN Sessions s ON e.event_id = s.event_id
WHERE TIME(s.start_time) BETWEEN '10:00:00' AND '12:00:00'
GROUP BY e.event_id, e.title;

/* 5. Most Active Cities */
SELECT u.city, COUNT(DISTINCT r.user_id) as active_users
FROM Registrations r
JOIN Users u ON r.user_id = u.user_id
GROUP BY u.city
ORDER BY active_users DESC
LIMIT 5;

/* 6. Event Resource Summary */
SELECT e.event_id, e.title,
       SUM(res.resource_type = 'pdf') as pdfs,
       SUM(res.resource_type = 'image') as images,
       SUM(res.resource_type = 'link') as links
FROM Events e
LEFT JOIN Resources res ON e.event_id = res.event_id
GROUP BY e.event_id, e.title;

/* 7. Low Feedback Alerts */
SELECT u.user_id, u.full_name, e.title, f.rating, f.comments
FROM Feedback f
JOIN Users u ON f.user_id = u.user_id
JOIN Events e ON f.event_id = e.event_id
WHERE f.rating < 3;

/* 8. Sessions per Upcoming Event */
SELECT e.event_id, e.title, COUNT(s.session_id) as session_count
FROM Events e
LEFT JOIN Sessions s ON e.event_id = s.event_id
WHERE e.status = 'upcoming'
GROUP BY e.event_id, e.title;

/* 9. Organizer Event Summary */
SELECT u.user_id, u.full_name,
       COUNT(e.event_id) as total_events,
       SUM(e.status = 'upcoming') as upcoming,
       SUM(e.status = 'completed') as completed,
       SUM(e.status = 'cancelled') as cancelled
FROM Users u
JOIN Events e ON u.user_id = e.organizer_id
GROUP BY u.user_id, u.full_name;

/* 10. Feedback Gap */
SELECT e.event_id, e.title, COUNT(r.registration_id) as registrations
FROM Events e
JOIN Registrations r ON e.event_id = r.event_id
LEFT JOIN Feedback f ON e.event_id = f.event_id
WHERE f.feedback_id IS NULL
GROUP BY e.event_id, e.title;

/* 11. Daily New User Count */
SELECT DATE(registration_date) as date, COUNT(*) as new_users
FROM Users
WHERE registration_date >= DATE_SUB(CURRENT_DATE(), INTERVAL 7 DAY)
GROUP BY DATE(registration_date)
ORDER BY date;

/* 12. Event with Maximum Sessions */
SELECT e.event_id, e.title, COUNT(s.session_id) as session_count
FROM Events e
JOIN Sessions s ON e.event_id = s.event_id
GROUP BY e.event_id, e.title
ORDER BY session_count DESC
LIMIT 1;

/* 13. Average Rating per City */
SELECT e.city, ROUND(AVG(f.rating),2) as avg_rating
FROM Events e
JOIN Feedback f ON e.event_id = f.event_id
GROUP BY e.city;

/* 14. Most Registered Events */
SELECT e.event_id, e.title, COUNT(r.registration_id) as registrations
FROM Events e
JOIN Registrations r ON e.event_id = r.event_id
GROUP BY e.event_id, e.title
ORDER BY registrations DESC
LIMIT 3;

/* 15. Event Session Time Conflicts */
SELECT s1.event_id, e.title,
       s1.session_id as session1, s1.title as session1_title,
       s2.session_id as session2, s2.title as session2_title,
       s1.start_time, s1.end_time
FROM Sessions s1
JOIN Sessions s2 ON s1.event_id = s2.event_id AND s1.session_id < s2.session_id
JOIN Events e ON s1.event_id = e.event_id
WHERE s1.start_time < s2.end_time AND s1.end_time > s2.start_time;

/* 16. Unregistered Active Users */
SELECT u.user_id, u.full_name, u.registration_date
FROM Users u
LEFT JOIN Registrations r ON u.user_id = r.user_id
WHERE u.registration_date >= DATE_SUB(CURRENT_DATE(), INTERVAL 30 DAY)
  AND r.registration_id IS NULL;

/* 17. Multi-Session Speakers */
SELECT speaker_name, COUNT(*) as session_count
FROM Sessions
GROUP BY speaker_name
HAVING COUNT(*) > 1;

/* 18. Events Without Resources */
SELECT e.event_id, e.title
FROM Events e
LEFT JOIN Resources r ON e.event_id = r.event_id
WHERE r.resource_id IS NULL;

/* 19. Completed Events Summary */
SELECT e.event_id, e.title,
       COUNT(r.registration_id) as registrations,
       ROUND(AVG(f.rating),2) as avg_rating
FROM Events e
LEFT JOIN Registrations r ON e.event_id = r.event_id
LEFT JOIN Feedback f ON e.event_id = f.event_id
WHERE e.status = 'completed'
GROUP BY e.event_id, e.title;

/* 20. User Engagement Index */
SELECT u.user_id, u.full_name,
       COUNT(DISTINCT r.event_id) as events_attended,
       COUNT(f.feedback_id) as feedbacks_given
FROM Users u
LEFT JOIN Registrations r ON u.user_id = r.user_id
LEFT JOIN Feedback f ON u.user_id = f.user_id
GROUP BY u.user_id, u.full_name;

/* 21. Top Feedback Providers */
SELECT u.user_id, u.full_name, COUNT(f.feedback_id) as feedback_count
FROM Users u
JOIN Feedback f ON u.user_id = f.user_id
GROUP BY u.user_id, u.full_name
ORDER BY feedback_count DESC
LIMIT 5;

/* 22. Duplicate Registrations Check */
SELECT user_id, event_id, COUNT(*) as duplicate_registrations
FROM Registrations
GROUP BY user_id, event_id
HAVING COUNT(*) > 1;

/* 23. Registration Trends - Monthly */
SELECT 
  DATE_FORMAT(registration_date, '%Y-%m') as month,
  COUNT(*) as registrations
FROM Registrations
WHERE registration_date >= DATE_SUB(CURRENT_DATE(), INTERVAL 12 MONTH)
GROUP BY DATE_FORMAT(registration_date, '%Y-%m')
ORDER BY month;

/* 24. Average Session Duration (minutes) */
SELECT 
  e.event_id, e.title,
  AVG(TIMESTAMPDIFF(MINUTE, start_time, end_time)) as avg_duration
FROM Events e
JOIN Sessions s ON e.event_id = s.event_id
GROUP BY e.event_id, e.title;

/* 25. Events Without Sessions */
SELECT e.event_id, e.title
FROM Events e
LEFT JOIN Sessions s ON e.event_id = s.event_id
WHERE s.session_id IS NULL;