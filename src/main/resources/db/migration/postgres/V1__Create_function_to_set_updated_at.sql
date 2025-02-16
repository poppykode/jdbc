-- Create a trigger function to update `updated_at` on row updates
CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();  -- Or CURRENT_TIMESTAMP
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
