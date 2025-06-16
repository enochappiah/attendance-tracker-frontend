import { useEffect, useState, type FormEvent } from 'react';
import axios from 'axios';

// Type Definitions
interface User {
    id: number;
    name: string;
}

interface Event {
    id: number;
    title: string;
    pointValue: number;
}

interface Summary {
    userName: string;
    totalPoints: number;
    requiredPoints: number;
}

function App() {
    const [users, setUsers] = useState<User[]>([]);
    const [events, setEvents] = useState<Event[]>([]);
    const [selectedUserId, setSelectedUserId] = useState<string>('');
    const [selectedEventId, setSelectedEventId] = useState<string>('');
    const [summary, setSummary] = useState<Summary | null>(null);

    useEffect(() => {
        axios.get<User[]>('/api/users').then((res) => setUsers(res.data));
        axios.get<Event[]>('/api/events').then((res) => setEvents(res.data));
    }, []);

    useEffect(() => {
        if (selectedUserId) {
            axios
                .get<Summary>(`/api/summary/${selectedUserId}`)
                .then((res) => setSummary(res.data));
        }
    }, [selectedUserId]);

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();

        await axios.post('/api/points/attend', {
            userId: parseInt(selectedUserId),
            eventId: parseInt(selectedEventId),
        });

        setSelectedEventId('');
        const res = await axios.get<Summary>(`/api/points/summary/${selectedUserId}`);
        setSummary(res.data);
    };

    return (
        <div style={{ padding: '2rem' }}>
            <h1>Attendance Tracker</h1>

            <form onSubmit={handleSubmit}>
                <label>
                    User:
                    <select
                        value={selectedUserId}
                        onChange={(e) => setSelectedUserId(e.target.value)}
                    >
                        <option value="">Select user</option>
                        {users.map((u) => (
                            <option key={u.id} value={u.id}>
                                {u.name}
                            </option>
                        ))}
                    </select>
                </label>

                <label style={{ marginLeft: '1rem' }}>
                    Event:
                    <select
                        value={selectedEventId}
                        onChange={(e) => setSelectedEventId(e.target.value)}
                    >
                        <option value="">Select event</option>
                        {events.map((ev) => (
                            <option key={ev.id} value={ev.id}>
                                {ev.title} ({ev.pointValue} pts)
                            </option>
                        ))}
                    </select>
                </label>

                <button
                    type="submit"
                    disabled={!selectedUserId || !selectedEventId}
                    style={{ marginLeft: '1rem' }}
                >
                    Attend
                </button>
            </form>

            {summary && (
                <div style={{ marginTop: '2rem' }}>
                    <h2>{summary.userName}'s Summary</h2>
                    <p>Total Points: {summary.totalPoints}</p>
                    <p>Required Points: {summary.requiredPoints}</p>
                </div>
            )}
        </div>
    );
}

export default App;
